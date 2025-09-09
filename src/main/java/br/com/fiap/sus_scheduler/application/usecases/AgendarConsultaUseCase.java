package br.com.fiap.sus_scheduler.application.usecases;

import br.com.fiap.sus_scheduler.domain.entity.Consulta;
import br.com.fiap.sus_scheduler.domain.enums.Especialidade;

import java.util.UUID;

public interface AgendarConsultaUseCase {
    Consulta executar(UUID pacienteId, Especialidade esp, boolean urgencia, Integer idade);
}
