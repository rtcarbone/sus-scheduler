package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.ConsultaGateway;
import br.com.fiap.sus_scheduler.application.usecase.CancelarConsultaUseCase;
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