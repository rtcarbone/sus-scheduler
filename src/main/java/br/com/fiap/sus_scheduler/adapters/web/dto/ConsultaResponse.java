package br.com.fiap.sus_scheduler.adapters.web.dto;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import br.com.fiap.sus_scheduler.domain.enums.Prioridade;
import br.com.fiap.sus_scheduler.domain.enums.StatusConsulta;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ConsultaResponse(UUID id,
                               UUID pacienteId,
                               UUID medicoId,
                               Especialidade especialidade,
                               Prioridade prioridade,
                               StatusConsulta status,
                               OffsetDateTime dataPrevista) {
}
