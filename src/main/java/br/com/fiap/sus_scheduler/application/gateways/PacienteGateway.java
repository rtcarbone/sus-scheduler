package br.com.fiap.sus_scheduler.application.gateways;

import br.com.fiap.sus_scheduler.domain.entity.Paciente;

import java.util.Optional;
import java.util.UUID;

public interface PacienteGateway {
    Paciente salvar(Paciente p);

    Optional<Paciente> buscarPorId(UUID id);

    Optional<Paciente> buscarPorCpf(String cpf);
}
