package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "MedicoRequest", description = "Payload para criação de médico")
public record MedicoRequest(
        @Schema(example = "Dr. Carlos")
        @NotBlank String nome,

        @Schema(example = "CARDIOLOGIA")
        @NotNull Especialidade especialidade,

        @Schema(example = "UBS Centro")
        @NotBlank String unidade
) {
}
