package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicoRequest(@NotBlank String nome,
                            @NotNull Especialidade especialidade,
                            @NotBlank String unidade) {
}
