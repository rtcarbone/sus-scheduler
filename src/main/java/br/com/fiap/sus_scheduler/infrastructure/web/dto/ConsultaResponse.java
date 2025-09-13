package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import br.com.fiap.sus_scheduler.domain.enums.Prioridade;
import br.com.fiap.sus_scheduler.domain.enums.StatusConsulta;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(name = "ConsultaResponse", description = "Retorno da API para consulta")
public record ConsultaResponse(
        @Schema(example = "550e8400-e29b-41d4-a716-446655440222") UUID id,
        @Schema(example = "550e8400-e29b-41d4-a716-446655440000") UUID pacienteId,
        @Schema(example = "550e8400-e29b-41d4-a716-446655440111") UUID medicoId,
        @Schema(example = "CARDIOLOGIA") Especialidade especialidade,
        @Schema(example = "ALTA") Prioridade prioridade,
        @Schema(example = "AGENDADA") StatusConsulta status,
        @Schema(example = "2025-09-14T10:00:00Z") OffsetDateTime dataPrevista
) {
}
