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

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AgendarConsultaUseCaseImpl implements AgendarConsultaUseCase {
    private final ConsultaGateway consultaGateway;
    private final PacienteGateway pacienteGateway;
    private final MedicoGateway medicoGateway;

    public Consulta executar(UUID pacienteId, Especialidade esp, boolean urgencia) {
        Paciente paciente = pacienteGateway.buscarPorId(pacienteId)
                .orElseThrow(() -> new PacienteNotFoundException(pacienteId));

        var medicos = medicoGateway.listarPorEspecialidade(esp);

        if (medicos.isEmpty()) throw new MedicoNaoDisponivelException(esp);

        Prioridade prioridade = calcularPrioridade(paciente, urgencia);

        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.UTC);

        Medico escolhido = medicos.stream()
                .min(Comparator.comparingLong(m -> consultaGateway.contarPorMedicoStatusAposData(m, StatusConsulta.AGENDADA, agora)))
                .orElseThrow(() -> new MedicoNaoDisponivelException(esp));

        long fila = consultaGateway.contarPorMedicoStatusAposData(escolhido, StatusConsulta.AGENDADA, agora);

        OffsetDateTime dataPrevista = agora.plusDays(Math.max(1, fila + (prioridade == Prioridade.ALTA ? 0 : 1)));

        return consultaGateway.salvar(Consulta.builder()
                                              .id(UUID.randomUUID())
                                              .paciente(paciente)
                                              .medico(escolhido)
                                              .especialidade(esp)
                                              .prioridade(prioridade)
                                              .status(StatusConsulta.AGENDADA)
                                              .dataPrevista(dataPrevista)
                                              .criadoEm(agora)
                                              .build());
    }

    private Prioridade calcularPrioridade(Paciente paciente, boolean urgencia) {
        if (urgencia) return Prioridade.ALTA;

        int idade = Period.between(paciente.getDataNascimento(), LocalDate.now())
                .getYears();
        if (idade >= 60) return Prioridade.MEDIA;

        return Prioridade.BAIXA;
    }
}