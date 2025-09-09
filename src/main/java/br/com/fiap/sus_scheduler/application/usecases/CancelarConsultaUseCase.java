package br.com.fiap.sus_scheduler.application.usecases;

import br.com.fiap.sus_scheduler.domain.entity.Consulta;

import java.util.UUID;

public interface CancelarConsultaUseCase {
    Consulta executar(UUID consultaId);
}
