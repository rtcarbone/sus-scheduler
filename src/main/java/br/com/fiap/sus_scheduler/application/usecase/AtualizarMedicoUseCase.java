package br.com.fiap.sus_scheduler.application.usecase;

import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.enums.Especialidade;

import java.util.UUID;

public interface AtualizarMedicoUseCase {
    Medico executar(UUID id, String nome, Especialidade especialidade, String unidade);
}
