package br.com.fiap.sus_scheduler.domain.exception;

import java.util.UUID;

public class MedicoNotFoundException extends RuntimeException {
    public MedicoNotFoundException(UUID id) {
        super("Médico com ID " + id + " não encontrado");
    }
}
