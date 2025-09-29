package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.ConsultaGateway;
import br.com.fiap.sus_scheduler.application.gateway.MedicoGateway;
import br.com.fiap.sus_scheduler.application.gateway.PacienteGateway;
import br.com.fiap.sus_scheduler.application.usecase.AgendarConsultaUseCase;
import br.com.fiap.sus_scheduler.domain.entity.Consulta;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import br.com.fiap.sus_scheduler.domain.enums.Prioridade;
import br.com.fiap.sus_scheduler.domain.enums.StatusConsulta;
import br.com.fiap.sus_scheduler.domain.exception.MedicoNaoDisponivelException;
import br.com.fiap.sus_scheduler.domain.exception.PacienteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AgendarConsultaUseCaseImpl implements AgendarConsultaUseCase {

    private static final int SLOT_MINUTES = 30;
    private static final int WORK_START_HOUR = 8;
    private static final int WORK_END_HOUR = 17;
    private static final ZoneOffset ZONE = ZoneOffset.UTC;

    private final ConsultaGateway consultaGateway;
    private final PacienteGateway pacienteGateway;
    private final MedicoGateway medicoGateway;

    // --- utilitários de calendário/expediente (inalterados) ---
    private static boolean ehDiaUtil(LocalDate d) {
        DayOfWeek w = d.getDayOfWeek();
        return w != DayOfWeek.SATURDAY && w != DayOfWeek.SUNDAY;
    }

    private static boolean estaNoExpediente(OffsetDateTime t) {
        int h = t.getHour();
        return h >= WORK_START_HOUR && h < WORK_END_HOUR;
    }

    private static OffsetDateTime inicioDiaUtil(OffsetDateTime t) {
        LocalDate d = t.toLocalDate();
        while (!ehDiaUtil(d)) d = d.plusDays(1);
        return d.atTime(WORK_START_HOUR, 0)
                .atOffset(ZONE);
    }

    private static OffsetDateTime inicioProximoDiaUtil(OffsetDateTime t) {
        LocalDate d = t.toLocalDate()
                .plusDays(1);
        while (!ehDiaUtil(d)) d = d.plusDays(1);
        return d.atTime(WORK_START_HOUR, 0)
                .atOffset(ZONE);
    }

    private static OffsetDateTime proximoInicioDeExpediente(OffsetDateTime t) {
        if (t.getHour() >= WORK_END_HOUR) return inicioProximoDiaUtil(t);
        return t.withHour(WORK_START_HOUR)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * arredonda para o próximo múltiplo de SLOT_MINUTES, respeitando expediente
     */
    private static OffsetDateTime alinharParaProximoSlot(OffsetDateTime t) {
        if (!ehDiaUtil(t.toLocalDate())) return inicioProximoDiaUtil(t);
        if (!estaNoExpediente(t)) return proximoInicioDeExpediente(t);

        int resto = t.getMinute() % SLOT_MINUTES;
        OffsetDateTime aligned = (resto == 0 ? t : t.plusMinutes(SLOT_MINUTES - resto))
                .withSecond(0)
                .withNano(0);
        if (!estaNoExpediente(aligned)) return proximoInicioDeExpediente(t);
        return aligned;
    }

    @Override
    public Consulta executar(UUID pacienteId, Especialidade esp, boolean urgencia) {
        Paciente paciente = pacienteGateway.buscarPorId(pacienteId)
                .orElseThrow(() -> new PacienteNotFoundException(pacienteId));

        List<Medico> medicos = medicoGateway.listarPorEspecialidade(esp);
        if (medicos.isEmpty()) throw new MedicoNaoDisponivelException(esp);

        Prioridade prioridadeNova = calcularPrioridade(paciente, urgencia);
        OffsetDateTime agora = OffsetDateTime.now(ZONE);

        // 1) escolhe o médico com menor fila a partir de agora
        Medico escolhido = medicos.stream()
                .min(Comparator.comparingLong(m ->
                                                      consultaGateway.contarPorMedicoStatusAposData(m, StatusConsulta.AGENDADA, agora)))
                .orElseThrow(() -> new MedicoNaoDisponivelException(esp));

        // 2) base temporal
        OffsetDateTime base = (prioridadeNova == Prioridade.ALTA)
                ? alinharParaProximoSlot(agora)
                : alinharParaProximoSlot(inicioDiaUtil(agora.plusDays(1)));

        // 3) encontra slot aplicando as regras de cancelamento por prioridade
        OffsetDateTime inicioEscolhido = alocarComCancelamento(escolhido, paciente, prioridadeNova, base);

        // 4) persiste a consulta vencedora
        Consulta nova = Consulta.builder()
                .id(UUID.randomUUID())
                .paciente(paciente)
                .medico(escolhido)
                .especialidade(esp)
                .prioridade(prioridadeNova)
                .status(StatusConsulta.AGENDADA)
                .dataPrevista(inicioEscolhido)
                .criadoEm(agora)
                .build();

        return consultaGateway.salvar(nova);
    }

    /**
     * Política nova:
     * - Se houver conflito e TODAS as consultas conflitantes tiverem prioridade MENOR que a nova:
     * -> cancela todas e usa o slot.
     * - Se houver pelo menos uma de prioridade IGUAL ou MAIOR:
     * -> não toma o slot; vai para o próximo.
     */
    private OffsetDateTime alocarComCancelamento(Medico medico,
                                                 Paciente paciente,
                                                 Prioridade prioridadeNova,
                                                 OffsetDateTime base) {

        OffsetDateTime t = alinharParaProximoSlot(base);

        while (true) {
            // normaliza para dia útil/expediente
            if (!ehDiaUtil(t.toLocalDate())) {
                t = inicioProximoDiaUtil(t);
                continue;
            }
            if (!estaNoExpediente(t)) {
                t = proximoInicioDeExpediente(t);
                continue;
            }

            OffsetDateTime fim = t.plusMinutes(SLOT_MINUTES);

            Optional<Consulta> conflitoMedico = consultaGateway.buscarAgendadaNoHorario(medico, t, fim);
            Optional<Consulta> conflitoPaciente = consultaGateway.buscarDoPacienteNoIntervalo(
                    paciente, t, fim, StatusConsulta.AGENDADA);

            // nenhum conflito → disponível
            if (conflitoMedico.isEmpty() && conflitoPaciente.isEmpty()) {
                return t;
            }

            // dedup (médico e paciente podem apontar à mesma consulta)
            Map<UUID, Consulta> conflitos = new LinkedHashMap<>();
            conflitoMedico.ifPresent(c -> conflitos.put(c.getId(), c));
            conflitoPaciente.ifPresent(c -> conflitos.put(c.getId(), c));

            // verificações de prioridade
            boolean todasMenores = conflitos.values()
                    .stream()
                    .allMatch(c -> prioridadeNova.compareTo(c.getPrioridade()) > 0);

            if (todasMenores) {
                // cancela todas as consultas conflitantes e toma o slot
                for (Consulta c : conflitos.values()) {
                    Consulta cancelada = c.cancelar();
                    consultaGateway.salvar(cancelada);
                }
                return t;
            }

            // existe pelo menos uma com prioridade igual/maior → avança
            t = t.plusMinutes(SLOT_MINUTES);
        }
    }

    private Prioridade calcularPrioridade(Paciente paciente, boolean urgencia) {
        if (urgencia) return Prioridade.ALTA;
        int idade = Period.between(paciente.getDataNascimento(), LocalDate.now())
                .getYears();
        if (idade >= 60) return Prioridade.MEDIA;
        return Prioridade.BAIXA;
    }
}