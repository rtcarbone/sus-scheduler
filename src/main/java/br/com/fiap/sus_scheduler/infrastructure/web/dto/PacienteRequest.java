package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Schema(name = "PacienteRequest", description = "Payload para criação de paciente")
public record PacienteRequest(
        @Schema(example = "Maria Souza")
        @NotBlank String nome,

        @Schema(example = "123.456.789-00")
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") String cpf,

        @Schema(example = "1973-05-12")
        @NotNull LocalDate dataNascimento
) {
}