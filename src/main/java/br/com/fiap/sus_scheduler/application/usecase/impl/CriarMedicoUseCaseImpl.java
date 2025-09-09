package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.MedicoGateway;
import br.com.fiap.sus_scheduler.application.usecase.CriarMedicoUseCase;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CriarMedicoUseCaseImpl implements CriarMedicoUseCase {
    private final MedicoGateway medicoGateway;

    public Medico executar(Medico medico) {
        return medicoGateway.salvar(medico);
    }
}
