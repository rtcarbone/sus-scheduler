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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
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

    public Consulta executar(UUID pacienteId, Especialidade esp, boolean urgencia, Integer idade) {
        Paciente paciente = pacienteGateway.buscarPorId(pacienteId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));

        var medicos = medicoGateway.listarPorEspecialidade(esp);

        if (medicos.isEmpty()) throw new IllegalArgumentException("Sem médicos para " + esp);

        Prioridade prioridade = urgencia ? Prioridade.ALTA : (idade != null && idade >= 60 ? Prioridade.MEDIA : Prioridade.BAIXA);

        OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.UTC);

        Medico escolhido = medicos.stream()
                .min(Comparator.comparingLong(m -> consultaGateway.contarPorMedicoStatusAposData(m, StatusConsulta.AGENDADA, agora)))
                .orElseThrow();

        long fila = consultaGateway.contarPorMedicoStatusAposData(escolhido, StatusConsulta.AGENDADA, agora);

        OffsetDateTime dataPrevista = agora.plusDays(Math.max(1, fila + (prioridade == Prioridade.ALTA ? 0 : 1)));

        return consultaGateway.salvar(Consulta.builder()
                                              .id(java.util.UUID.randomUUID())
                                              .paciente(paciente)
                                              .medico(escolhido)
                                              .especialidade(esp)
                                              .prioridade(prioridade)
                                              .status(StatusConsulta.AGENDADA)
                                              .dataPrevista(dataPrevista)
                                              .criadoEm(agora)
                                              .build());
    }
}