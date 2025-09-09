package br.com.fiap.sus_scheduler.application.gateway;

import br.com.fiap.sus_scheduler.domain.entity.Consulta;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import br.com.fiap.sus_scheduler.domain.enums.StatusConsulta;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ConsultaGateway {
    Consulta salvar(Consulta c);

    Optional<Consulta> buscarPorId(java.util.UUID id);

    List<Consulta> listarPorPaciente(Paciente p);

    long contarPorMedicoStatusAposData(Medico m, StatusConsulta s, OffsetDateTime after);
}
