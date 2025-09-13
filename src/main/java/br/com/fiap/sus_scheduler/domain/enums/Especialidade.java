package br.com.fiap.sus_scheduler.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Especialidades médicas disponíveis para agendamento")
public enum Especialidade {

    @Schema(description = "Clínico Geral")
    CLINICO_GERAL,

    @Schema(description = "Cardiologia")
    CARDIOLOGIA,

    @Schema(description = "Pediatria")
    PEDIATRIA,

    @Schema(description = "Ortopedia")
    ORTOPEDIA,

    @Schema(description = "Ginecologia")
    GINECOLOGIA
}
