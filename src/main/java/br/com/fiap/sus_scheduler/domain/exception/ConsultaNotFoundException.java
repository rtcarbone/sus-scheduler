package br.com.fiap.sus_scheduler.domain.exception;

import java.util.UUID;

public class ConsultaNotFoundException extends RuntimeException {
    public ConsultaNotFoundException(UUID id) {
        super("Consulta com ID " + id + " não encontrada");
    }
}
