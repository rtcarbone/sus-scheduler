package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MedicoUpdateRequest", description = "Payload para atualização de médico")
public record MedicoUpdateRequest(
        @Schema(example = "Dra. Ana") String nome,
        @Schema(example = "PEDIATRIA") Especialidade especialidade,
        @Schema(example = "UBS Norte") String unidade
) {
}
