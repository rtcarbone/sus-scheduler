package br.com.fiap.sus_scheduler.adapters.persistence.entity;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import br.com.fiap.sus_scheduler.domain.enums.Prioridade;
import br.com.fiap.sus_scheduler.domain.enums.StatusConsulta;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "consultas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultaJpa {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteJpa paciente;
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private MedicoJpa medico;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private Especialidade especialidade;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Prioridade prioridade;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private StatusConsulta status;
    @Column(nullable = false)
    private OffsetDateTime dataPrevista;
    @Column(nullable = false)
    private OffsetDateTime criadoEm;

    @PrePersist
    public void pre() {
        if (id == null) id = UUID.randomUUID();
        if (criadoEm == null) criadoEm = OffsetDateTime.now();
    }
}