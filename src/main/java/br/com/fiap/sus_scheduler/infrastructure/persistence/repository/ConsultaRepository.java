package br.com.fiap.sus_scheduler.infrastructure.persistence.repository;

import br.com.fiap.sus_scheduler.domain.enums.StatusConsulta;
import br.com.fiap.sus_scheduler.infrastructure.persistence.entity.ConsultaJpa;
import br.com.fiap.sus_scheduler.infrastructure.persistence.entity.PacienteJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultaRepository extends JpaRepository<ConsultaJpa, UUID> {

    List<ConsultaJpa> findByPaciente(PacienteJpa paciente);

    long countByMedicoIdAndStatusAndDataPrevistaAfter(
            UUID medicoId, StatusConsulta status, OffsetDateTime after);

    boolean existsByMedicoIdAndStatusAndDataPrevistaGreaterThanEqualAndDataPrevistaLessThan(
            UUID medicoId, StatusConsulta status,
            OffsetDateTime inicio, OffsetDateTime fimExclusivo);

    Optional<ConsultaJpa> findByMedicoIdAndStatusAndDataPrevistaGreaterThanEqualAndDataPrevistaLessThan(
            UUID medicoId, StatusConsulta status,
            OffsetDateTime inicio, OffsetDateTime fimExclusivo);

    Optional<ConsultaJpa>
    findFirstByPacienteIdAndStatusAndDataPrevistaGreaterThanEqualAndDataPrevistaLessThanOrderByDataPrevistaAsc(
            UUID pacienteId,
            StatusConsulta status,
            OffsetDateTime inicio,
            OffsetDateTime fimExclusivo
    );
}