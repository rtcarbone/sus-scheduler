package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.PacienteGateway;
import br.com.fiap.sus_scheduler.application.usecase.ListarPacientesUseCase;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListarPacientesUseCaseImpl implements ListarPacientesUseCase {
    private final PacienteGateway gateway;

    @Override
    public List<Paciente> executar(String cpf) {
        if (cpf != null && !cpf.isBlank()) {
            return gateway.buscarPorCpf(cpf)
                    .map(List::of)
                    .orElseGet(List::of);
        }
        return gateway.listarTodos();
    }
}
