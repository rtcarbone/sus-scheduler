package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "Formato de erro padrão da API")
public record ErrorResponse(
        @Schema(example = "2025-09-13T16:00:00Z") String timestamp,
        @Schema(example = "400") int status,
        @Schema(example = "Bad Request") String error,
        @Schema(example = "Paciente não encontrado") String message
) {
}
