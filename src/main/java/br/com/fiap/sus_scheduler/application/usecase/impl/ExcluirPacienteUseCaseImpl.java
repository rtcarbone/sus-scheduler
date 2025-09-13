package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.PacienteGateway;
import br.com.fiap.sus_scheduler.application.usecase.ExcluirPacienteUseCase;
import br.com.fiap.sus_scheduler.domain.exception.PacienteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ExcluirPacienteUseCaseImpl implements ExcluirPacienteUseCase {
    private final PacienteGateway gateway;

    @Override
    public void executar(UUID id) {
        gateway.buscarPorId(id)
                .orElseThrow(() -> new PacienteNotFoundException(id));
        gateway.deletarPorId(id);
    }
}
