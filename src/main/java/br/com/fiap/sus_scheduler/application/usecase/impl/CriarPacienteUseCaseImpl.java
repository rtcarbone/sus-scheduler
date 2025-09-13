package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.PacienteGateway;
import br.com.fiap.sus_scheduler.application.usecase.CriarPacienteUseCase;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import br.com.fiap.sus_scheduler.domain.exception.CpfDuplicadoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CriarPacienteUseCaseImpl implements CriarPacienteUseCase {
    private final PacienteGateway pacienteGateway;

    public Paciente executar(Paciente paciente) {
        var existente = pacienteGateway.buscarPorCpf(paciente.getCpf());

        if (existente.isPresent()) {
            throw new CpfDuplicadoException(paciente.getCpf());
        }

        return pacienteGateway.salvar(paciente);

    }
}
