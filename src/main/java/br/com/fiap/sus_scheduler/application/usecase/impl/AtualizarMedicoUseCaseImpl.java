package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.MedicoGateway;
import br.com.fiap.sus_scheduler.application.usecase.AtualizarMedicoUseCase;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AtualizarMedicoUseCaseImpl implements AtualizarMedicoUseCase {
    private final MedicoGateway gateway;

    public Medico executar(UUID id, String nome, Especialidade especialidade, String unidade) {
        var atual = gateway.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));
        var atualizado = Medico.builder()
                .id(atual.getId())
                .nome(nome != null ? nome : atual.getNome())
                .especialidade(especialidade != null ? especialidade : atual.getEspecialidade())
                .unidade(unidade != null ? unidade : atual.getUnidade())
                .build();
        return gateway.salvar(atualizado);
    }
}
