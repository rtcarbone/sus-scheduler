package br.com.fiap.sus_scheduler.application.usecases;

import br.com.fiap.sus_scheduler.domain.entity.Paciente;

public interface CriarPacienteUseCase {
    Paciente executar(Paciente paciente);
}
