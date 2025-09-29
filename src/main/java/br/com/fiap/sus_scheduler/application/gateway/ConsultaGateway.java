package br.com.fiap.sus_scheduler.application.gateway;

import br.com.fiap.sus_scheduler.domain.entity.Consulta;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import br.com.fiap.sus_scheduler.domain.enums.StatusConsulta;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultaGateway {
    long contarPorMedicoStatusAposData(Medico medico, StatusConsulta status, OffsetDateTime data);

    Optional<Consulta> buscarAgendadaNoHorario(Medico medico, OffsetDateTime inicio, OffsetDateTime fim);

    Consulta salvar(Consulta consulta);

    Consulta atualizar(Consulta consulta);

    Optional<Consulta> buscarPorId(UUID id);

    List<Consulta> listarPorPaciente(Paciente p);

    Optional<Consulta> buscarDoPacienteNoIntervalo(Paciente paciente,
                                                   OffsetDateTime inicio,
                                                   OffsetDateTime fimExclusivo,
                                                   StatusConsulta status);
}
