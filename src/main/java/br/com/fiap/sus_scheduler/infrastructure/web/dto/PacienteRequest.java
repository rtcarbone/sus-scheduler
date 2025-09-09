package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record PacienteRequest(@NotBlank String nome,
                              @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") String cpf,
                              @NotNull LocalDate dataNascimento) {
}
