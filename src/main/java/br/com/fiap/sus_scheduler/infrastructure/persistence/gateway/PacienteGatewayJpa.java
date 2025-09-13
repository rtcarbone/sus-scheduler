package br.com.fiap.sus_scheduler.infrastructure.persistence.gateway;

import br.com.fiap.sus_scheduler.application.gateway.PacienteGateway;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import br.com.fiap.sus_scheduler.infrastructure.persistence.mapper.PersistenceMapper;
import br.com.fiap.sus_scheduler.infrastructure.persistence.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PacienteGatewayJpa implements PacienteGateway {
    private final PacienteRepository repo;
    private final PersistenceMapper mapper;

    public Paciente salvar(Paciente p) {
        return mapper.toDomain(repo.save(mapper.toJpa(p)));
    }

    public Optional<Paciente> buscarPorId(UUID id) {
        return repo.findById(id)
                .map(mapper::toDomain);
    }

    public Optional<Paciente> buscarPorCpf(String cpf) {
        return repo.findByCpf(cpf)
                .map(mapper::toDomain);
    }

    @Override
    public List<Paciente> listarTodos() {
        return repo.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deletarPorId(UUID id) {
        repo.deleteById(id);
    }

}
