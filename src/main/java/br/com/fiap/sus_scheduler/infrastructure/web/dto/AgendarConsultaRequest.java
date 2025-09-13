package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(name = "AgendarConsultaRequest", description = "Payload para agendamento de consulta")
public record AgendarConsultaRequest(
        @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull UUID pacienteId,

        @Schema(example = "CARDIOLOGIA")
        @NotNull Especialidade especialidade,

        @Schema(example = "true")
        boolean urgencia
) {
}
