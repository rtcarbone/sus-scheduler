package br.com.fiap.sus_scheduler.application.usecase;

import br.com.fiap.sus_scheduler.domain.entity.Medico;

public interface CriarMedicoUseCase {
    Medico executar(Medico medico);
}
