package br.com.fiap.sus_scheduler.adapters.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacienteJpa {
    @Id
    private UUID id;
    @Column(nullable = false, length = 120)
    private String nome;
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @PrePersist
    public void pre() {
        if (id == null) id = UUID.randomUUID();
    }
}
