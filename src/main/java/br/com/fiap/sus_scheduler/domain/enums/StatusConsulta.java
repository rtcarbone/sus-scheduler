package br.com.fiap.sus_scheduler.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status possíveis de uma consulta")
public enum StatusConsulta {

    @Schema(description = "Consulta agendada e ainda não realizada")
    AGENDADA,

    @Schema(description = "Consulta cancelada")
    CANCELADA
}
