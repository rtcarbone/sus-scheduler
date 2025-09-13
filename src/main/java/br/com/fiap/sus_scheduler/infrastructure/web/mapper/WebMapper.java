package br.com.fiap.sus_scheduler.infrastructure.web.mapper;

import br.com.fiap.sus_scheduler.domain.entity.Consulta;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WebMapper {
    Paciente toDomain(PacienteRequest req);

    PacienteResponse toResponse(Paciente paciente);

    Medico toDomain(MedicoRequest req);

    MedicoResponse toResponse(Medico medico);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "medicoId", source = "medico.id")
    ConsultaResponse toResponse(Consulta c);
}
