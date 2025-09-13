package br.com.fiap.sus_scheduler.application.usecase;

import br.com.fiap.sus_scheduler.domain.entity.Paciente;

import java.util.UUID;

public interface BuscarPacientePorIdUseCase {
    Paciente executar(UUID id);
}