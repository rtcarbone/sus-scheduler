package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "PacienteUpdateRequest", description = "Payload para atualização de paciente")
public record PacienteUpdateRequest(
        @Schema(example = "João Silva") String nome,
        @Schema(example = "987.654.321-00") String cpf,
        @Schema(example = "1990-10-30") LocalDate dataNascimento
) {
}
