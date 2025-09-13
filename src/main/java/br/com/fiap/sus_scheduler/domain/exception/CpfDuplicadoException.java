package br.com.fiap.sus_scheduler.domain.exception;

public class CpfDuplicadoException extends RuntimeException {
    public CpfDuplicadoException(String cpf) {
        super("Já existe outro paciente com o CPF " + cpf);
    }
}
