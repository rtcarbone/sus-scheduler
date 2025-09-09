package br.com.fiap.sus_scheduler.infrastructure.persistence.gateway;

import br.com.fiap.sus_scheduler.infrastructure.persistence.mapper.PersistenceMapper;
import br.com.fiap.sus_scheduler.infrastructure.persistence.repository.ConsultaRepository;
import br.com.fiap.sus_scheduler.infrastructure.persistence.repository.MedicoRepository;
import br.com.fiap.sus_scheduler.infrastructure.persistence.repository.PacienteRepository;
import br.com.fiap.sus_scheduler.application.gateways.ConsultaGateway;
import br.com.fiap.sus_scheduler.domain.entity.Consulta;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import br.com.fiap.sus_scheduler.domain.enums.StatusConsulta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ConsultaGatewayJpa implements ConsultaGateway {
    private final ConsultaRepository repo;
    private final PacienteRepository pacienteRepo;
    private final MedicoRepository medicoRepo;
    private final PersistenceMapper mapper;

    public Consulta salvar(Consulta c) {
        return mapper.toDomain(repo.save(mapper.toJpa(c)));
    }

    public Optional<Consulta> buscarPorId(UUID id) {
        return repo.findById(id)
                .map(mapper::toDomain);
    }

    public List<Consulta> listarPorPaciente(Paciente p) {
        var pj = pacienteRepo.findById(p.getId())
                .orElseThrow();
        return repo.findByPaciente(pj)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    public long contarPorMedicoStatusAposData(Medico m, StatusConsulta s, OffsetDateTime after) {
        var mj = medicoRepo.findById(m.getId())
                .orElseThrow();
        return repo.countByMedicoAndStatusAndDataPrevistaAfter(mj, s, after);
    }
}