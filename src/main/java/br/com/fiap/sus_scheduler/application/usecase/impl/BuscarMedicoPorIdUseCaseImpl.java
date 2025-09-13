package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.MedicoGateway;
import br.com.fiap.sus_scheduler.application.usecase.BuscarMedicoPorIdUseCase;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuscarMedicoPorIdUseCaseImpl implements BuscarMedicoPorIdUseCase {
    private final MedicoGateway gateway;

    public Medico executar(UUID id) {
        return gateway.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));
    }
}