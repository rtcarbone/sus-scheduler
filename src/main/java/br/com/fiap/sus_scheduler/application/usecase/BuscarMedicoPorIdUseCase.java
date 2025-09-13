package br.com.fiap.sus_scheduler.application.usecase;

import br.com.fiap.sus_scheduler.domain.entity.Medico;

import java.util.UUID;

public interface BuscarMedicoPorIdUseCase {
    Medico executar(UUID id);
}
