package br.com.fiap.sus_scheduler.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Níveis de prioridade atribuídos a uma consulta")
public enum Prioridade {

    @Schema(description = "Baixa prioridade (casos comuns)")
    BAIXA,

    @Schema(description = "Média prioridade (ex.: idosos)")
    MEDIA,

    @Schema(description = "Alta prioridade (ex.: urgência)")
    ALTA
}
