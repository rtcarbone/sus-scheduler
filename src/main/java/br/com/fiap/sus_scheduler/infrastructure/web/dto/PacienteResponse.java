package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "PacienteResponse", description = "Retorno da API para paciente")
public record PacienteResponse(
        @Schema(example = "550e8400-e29b-41d4-a716-446655440000") UUID id,
        @Schema(example = "Maria Souza") String nome,
        @Schema(example = "123.456.789-00") String cpf,
        @Schema(example = "1973-05-12") LocalDate dataNascimento
) {
}
