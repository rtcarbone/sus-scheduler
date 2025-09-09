package br.com.fiap.sus_scheduler.infrastructure.web.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PacienteResponse(UUID id,
                               String nome,
                               String cpf,
                               LocalDate dataNascimento) {
}
