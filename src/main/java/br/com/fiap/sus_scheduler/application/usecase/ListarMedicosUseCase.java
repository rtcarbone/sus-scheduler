package br.com.fiap.sus_scheduler.application.usecase;

import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.enums.Especialidade;

import java.util.List;

public interface ListarMedicosUseCase {
    List<Medico> executar(Especialidade especialidade);
}
