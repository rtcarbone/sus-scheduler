package br.com.fiap.sus_scheduler.application.usecases;

import br.com.fiap.sus_scheduler.domain.entity.Consulta;

import java.util.List;
import java.util.UUID;

public interface ListarConsultasPorPacienteUseCase {
    List<Consulta> executar(UUID pacienteId);
}
