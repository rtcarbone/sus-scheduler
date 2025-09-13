package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.MedicoGateway;
import br.com.fiap.sus_scheduler.application.usecase.ExcluirMedicoUseCase;
import br.com.fiap.sus_scheduler.domain.exception.MedicoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ExcluirMedicoUseCaseImpl implements ExcluirMedicoUseCase {
    private final MedicoGateway gateway;

    public void executar(UUID id) {
        gateway.buscarPorId(id)
                .orElseThrow(() -> new MedicoNotFoundException(id));
        gateway.deletarPorId(id);
    }
}
