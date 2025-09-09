package br.com.fiap.sus_scheduler.adapters.web.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PacienteResponse(UUID id,
                               String nome,
                               String cpf,
                               LocalDate dataNascimento) {
}
