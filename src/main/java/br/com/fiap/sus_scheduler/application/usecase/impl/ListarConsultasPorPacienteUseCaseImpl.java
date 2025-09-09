package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.ConsultaGateway;
import br.com.fiap.sus_scheduler.application.gateway.PacienteGateway;
import br.com.fiap.sus_scheduler.application.usecase.ListarConsultasPorPacienteUseCase;
import br.com.fiap.sus_scheduler.domain.entity.Consulta;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListarConsultasPorPacienteUseCaseImpl implements ListarConsultasPorPacienteUseCase {
    private final ConsultaGateway consultaGateway;
    private final PacienteGateway pacienteGateway;

    public List<Consulta> executar(UUID pacienteId) {
        Paciente p = pacienteGateway.buscarPorId(pacienteId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
        return consultaGateway.listarPorPaciente(p);
    }
}
