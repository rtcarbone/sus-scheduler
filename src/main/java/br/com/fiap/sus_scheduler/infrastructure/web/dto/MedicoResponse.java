package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "MedicoResponse", description = "Retorno da API para médico")
public record MedicoResponse(
        @Schema(example = "550e8400-e29b-41d4-a716-446655440111") UUID id,
        @Schema(example = "Dr. Carlos") String nome,
        @Schema(example = "CARDIOLOGIA") Especialidade especialidade,
        @Schema(example = "UBS Centro") String unidade
) {
}
