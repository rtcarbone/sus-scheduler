package br.com.fiap.sus_scheduler.adapters.persistence.mapper;

import br.com.fiap.sus_scheduler.adapters.persistence.entity.ConsultaJpa;
import br.com.fiap.sus_scheduler.adapters.persistence.entity.MedicoJpa;
import br.com.fiap.sus_scheduler.adapters.persistence.entity.PacienteJpa;
import br.com.fiap.sus_scheduler.domain.entity.Consulta;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersistenceMapper {
    Paciente toDomain(PacienteJpa jpa);

    PacienteJpa toJpa(Paciente d);

    Medico toDomain(MedicoJpa jpa);

    MedicoJpa toJpa(Medico d);

    @Mapping(target = "paciente", source = "paciente")
    @Mapping(target = "medico", source = "medico")
    Consulta toDomain(ConsultaJpa jpa);

    ConsultaJpa toJpa(Consulta d);
}