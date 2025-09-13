package br.com.fiap.sus_scheduler.application.usecase;

import br.com.fiap.sus_scheduler.domain.entity.Paciente;

import java.time.LocalDate;
import java.util.UUID;

public interface AtualizarPacienteUseCase {
    Paciente executar(UUID id, String nome, String cpf, LocalDate dataNascimento);
}
