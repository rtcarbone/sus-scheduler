package br.com.fiap.sus_scheduler.adapters.web.dto;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AgendarConsultaRequest(@NotNull UUID pacienteId,
                                     @NotNull Especialidade especialidade,
                                     boolean urgencia,
                                     Integer idade) {
}
