package br.com.fiap.sus_scheduler.adapters.persistence.repository;

import br.com.fiap.sus_scheduler.adapters.persistence.entity.MedicoJpa;
import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MedicoRepository extends JpaRepository<MedicoJpa, UUID> {
    List<MedicoJpa> findByEspecialidade(Especialidade esp);
}
