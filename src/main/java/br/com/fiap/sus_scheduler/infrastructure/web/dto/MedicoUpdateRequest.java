package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;

public record MedicoUpdateRequest(
        String nome,
        Especialidade especialidade,
        String unidade
) {
}
