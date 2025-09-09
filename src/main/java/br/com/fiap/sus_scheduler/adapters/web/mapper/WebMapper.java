package br.com.fiap.sus_scheduler.adapters.web.mapper;

import br.com.fiap.sus_scheduler.adapters.web.dto.ConsultaResponse;
import br.com.fiap.sus_scheduler.adapters.web.dto.MedicoRequest;
import br.com.fiap.sus_scheduler.adapters.web.dto.PacienteRequest;
import br.com.fiap.sus_scheduler.domain.entity.Consulta;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WebMapper {
    Paciente toDomain(PacienteRequest req);

    Medico toDomain(MedicoRequest req);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "medicoId", source = "medico.id")
    ConsultaResponse toResponse(Consulta c);
}
