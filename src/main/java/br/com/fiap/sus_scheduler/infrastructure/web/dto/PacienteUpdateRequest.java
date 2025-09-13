package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import java.time.LocalDate;

public record PacienteUpdateRequest(
        String nome,
        String cpf,
        LocalDate dataNascimento
) {
}
