package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.PacienteGateway;
import br.com.fiap.sus_scheduler.application.usecase.BuscarPacientePorIdUseCase;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import br.com.fiap.sus_scheduler.domain.exception.PacienteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuscarPacientePorIdUseCaseImpl implements BuscarPacientePorIdUseCase {
    private final PacienteGateway gateway;

    @Override
    public Paciente executar(UUID id) {
        return gateway.buscarPorId(id)
                .orElseThrow(() -> new PacienteNotFoundException(id));
    }
}
