package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.PacienteGateway;
import br.com.fiap.sus_scheduler.application.usecase.CriarPacienteUseCase;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CriarPacienteUseCaseImpl implements CriarPacienteUseCase {
    private final PacienteGateway pacienteGateway;

    public Paciente executar(Paciente paciente) {
        return pacienteGateway.salvar(paciente);
    }
}
