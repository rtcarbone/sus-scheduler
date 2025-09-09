package br.com.fiap.sus_scheduler.domain.entity;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import br.com.fiap.sus_scheduler.domain.enums.Prioridade;
import br.com.fiap.sus_scheduler.domain.enums.StatusConsulta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Consulta {
    private final UUID id;
    private final Paciente paciente;
    private final Medico medico;
    private final Especialidade especialidade;
    private final Prioridade prioridade;
    private final StatusConsulta status;
    private final OffsetDateTime dataPrevista;
    private final OffsetDateTime criadoEm;

    public Consulta cancelar() {
        return Consulta.builder()
                .id(id)
                .paciente(paciente)
                .medico(medico)
                .especialidade(especialidade)
                .prioridade(prioridade)
                .status(StatusConsulta.CANCELADA)
                .dataPrevista(dataPrevista)
                .criadoEm(criadoEm)
                .build();
    }
}
