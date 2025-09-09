package br.com.fiap.sus_scheduler.application.usecases.impl;

import br.com.fiap.sus_scheduler.application.gateways.ConsultaGateway;
import br.com.fiap.sus_scheduler.application.usecases.CancelarConsultaUseCase;
import br.com.fiap.sus_scheduler.domain.entity.Consulta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CancelarConsultaUseCaseImpl implements CancelarConsultaUseCase {
    private final ConsultaGateway consultaGateway;

    public Consulta executar(UUID consultaId) {
        Consulta atual = consultaGateway.buscarPorId(consultaId)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
        return consultaGateway.salvar(atual.cancelar());
    }
}