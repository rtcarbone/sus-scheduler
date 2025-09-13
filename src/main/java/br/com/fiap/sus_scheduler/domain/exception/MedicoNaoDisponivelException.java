package br.com.fiap.sus_scheduler.domain.exception;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;

public class MedicoNaoDisponivelException extends RuntimeException {
    public MedicoNaoDisponivelException(Especialidade esp) {
        super("Nenhum médico disponível para a especialidade " + esp);
    }
}
