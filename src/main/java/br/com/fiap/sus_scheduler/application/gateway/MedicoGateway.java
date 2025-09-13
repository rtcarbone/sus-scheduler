package br.com.fiap.sus_scheduler.application.gateway;

import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.enums.Especialidade;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MedicoGateway {
    Medico salvar(Medico m);

    Optional<Medico> buscarPorId(UUID id);

    List<Medico> listarTodos();

    List<Medico> listarPorEspecialidade(Especialidade esp);

    void deletarPorId(UUID id);
}
