package br.com.fiap.sus_scheduler.infrastructure.persistence.gateway;

import br.com.fiap.sus_scheduler.infrastructure.persistence.mapper.PersistenceMapper;
import br.com.fiap.sus_scheduler.infrastructure.persistence.repository.MedicoRepository;
import br.com.fiap.sus_scheduler.application.gateways.MedicoGateway;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MedicoGatewayJpa implements MedicoGateway {
    private final MedicoRepository repo;
    private final PersistenceMapper mapper;

    public Medico salvar(Medico m) {
        return mapper.toDomain(repo.save(mapper.toJpa(m)));
    }

    public Optional<Medico> buscarPorId(UUID id) {
        return repo.findById(id)
                .map(mapper::toDomain);
    }

    public List<Medico> listarPorEspecialidade(Especialidade esp) {
        return repo.findByEspecialidade(esp)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
