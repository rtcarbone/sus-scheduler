package br.com.fiap.sus_scheduler.application.usecase;

import br.com.fiap.sus_scheduler.domain.entity.Paciente;

import java.util.List;

public interface ListarPacientesUseCase {
    List<Paciente> executar(String cpf);
}
