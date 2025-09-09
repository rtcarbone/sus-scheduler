package br.com.fiap.sus_scheduler.adapters.web.dto;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;

import java.util.UUID;

public record MedicoResponse(UUID id,
                             String nome,
                             Especialidade especialidade,
                             String unidade) {
}
