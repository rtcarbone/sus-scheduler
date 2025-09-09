package br.com.fiap.sus_scheduler.application.gateways;

import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.enums.Especialidade;

import java.util.List;
import java.util.Optional;

public interface MedicoGateway {
    Medico salvar(Medico m);

    Optional<Medico> buscarPorId(java.util.UUID id);

    List<Medico> listarPorEspecialidade(Especialidade esp);
}
