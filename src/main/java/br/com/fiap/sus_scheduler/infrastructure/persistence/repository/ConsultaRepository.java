package br.com.fiap.sus_scheduler.infrastructure.persistence.repository;

import br.com.fiap.sus_scheduler.infrastructure.persistence.entity.ConsultaJpa;
import br.com.fiap.sus_scheduler.infrastructure.persistence.entity.MedicoJpa;
import br.com.fiap.sus_scheduler.infrastructure.persistence.entity.PacienteJpa;
import br.com.fiap.sus_scheduler.domain.enums.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface ConsultaRepository extends JpaRepository<ConsultaJpa, UUID> {
    List<ConsultaJpa> findByPaciente(PacienteJpa paciente);

    long countByMedicoAndStatusAndDataPrevistaAfter(MedicoJpa medico, StatusConsulta status, OffsetDateTime after);
}