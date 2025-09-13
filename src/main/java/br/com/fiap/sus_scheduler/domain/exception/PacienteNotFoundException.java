package br.com.fiap.sus_scheduler.domain.exception;

import java.util.UUID;

public class PacienteNotFoundException extends RuntimeException {
    public PacienteNotFoundException(UUID id) {
        super("Paciente com ID " + id + " não encontrado");
    }
}
