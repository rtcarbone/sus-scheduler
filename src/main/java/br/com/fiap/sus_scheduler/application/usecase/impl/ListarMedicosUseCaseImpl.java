package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.MedicoGateway;
import br.com.fiap.sus_scheduler.application.usecase.ListarMedicosUseCase;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListarMedicosUseCaseImpl implements ListarMedicosUseCase {
    private final MedicoGateway gateway;

    public List<Medico> executar(Especialidade especialidade) {
        if (especialidade != null) return gateway.listarPorEspecialidade(especialidade);
        return gateway.listarTodos();
    }
}