package br.com.fiap.sus_scheduler.infrastructure.persistence.repository;

import br.com.fiap.sus_scheduler.infrastructure.persistence.entity.PacienteJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PacienteRepository extends JpaRepository<PacienteJpa, UUID> {
    Optional<PacienteJpa> findByCpf(String cpf);
}
