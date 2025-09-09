package br.com.fiap.sus_scheduler.adapters.persistence.entity;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "medicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoJpa {
    @Id
    private UUID id;
    @Column(nullable = false, length = 120)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private Especialidade especialidade;
    @Column(nullable = false, length = 120)
    private String unidade;

    @PrePersist
    public void pre() {
        if (id == null) id = UUID.randomUUID();
    }
}
