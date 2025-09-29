package br.com.fiap.sus_scheduler.infrastructure.persistence.gateway;

import br.com.fiap.sus_scheduler.application.gateway.ConsultaGateway;
import br.com.fiap.sus_scheduler.domain.entity.Consulta;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import br.com.fiap.sus_scheduler.domain.enums.StatusConsulta;
import br.com.fiap.sus_scheduler.infrastructure.persistence.mapper.PersistenceMapper;
import br.com.fiap.sus_scheduler.infrastructure.persistence.repository.ConsultaRepository;
import br.com.fiap.sus_scheduler.infrastructure.persistence.repository.MedicoRepository;
import br.com.fiap.sus_scheduler.infrastructure.persistence.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ConsultaGatewayJpa implements ConsultaGateway {

    private static final StatusConsulta PADRAO_STATUS = StatusConsulta.AGENDADA;

    private final ConsultaRepository repo;
    private final PacienteRepository pacienteRepo;
    private final MedicoRepository medicoRepo; // ainda usado no listarPorPaciente; dá pra remover se trocar o finder por pacienteId
    private final PersistenceMapper mapper;

    @Override
    public Consulta salvar(Consulta c) {
        return mapper.toDomain(repo.save(mapper.toJpa(c)));
    }

    @Override
    public Consulta atualizar(Consulta c) {
        return mapper.toDomain(repo.save(mapper.toJpa(c)));
    }

    @Override
    public Optional<Consulta> buscarPorId(UUID id) {
        return repo.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Consulta> listarPorPaciente(Paciente p) {
        var pj = pacienteRepo.findById(p.getId())
                .orElseThrow();
        return repo.findByPaciente(pj)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public long contarPorMedicoStatusAposData(Medico m, StatusConsulta s, OffsetDateTime data) {
        return repo.countByMedicoIdAndStatusAndDataPrevistaAfter(m.getId(), s, data);
    }

    @Override
    public Optional<Consulta> buscarAgendadaNoHorario(Medico medico,
                                                      OffsetDateTime inicio,
                                                      OffsetDateTime fimExclusivo) {
        return repo.findByMedicoIdAndStatusAndDataPrevistaGreaterThanEqualAndDataPrevistaLessThan(
                        medico.getId(), PADRAO_STATUS, inicio, fimExclusivo
                )
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Consulta> buscarDoPacienteNoIntervalo(Paciente paciente,
                                                          OffsetDateTime inicio,
                                                          OffsetDateTime fimExclusivo,
                                                          StatusConsulta status) {
        return repo
                .findFirstByPacienteIdAndStatusAndDataPrevistaGreaterThanEqualAndDataPrevistaLessThanOrderByDataPrevistaAsc(
                        paciente.getId(), status, inicio, fimExclusivo)
                .map(mapper::toDomain);
    }
}