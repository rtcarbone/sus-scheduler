package br.com.fiap.sus_scheduler.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Paciente {
    private final UUID id;
    private final String nome;
    private final String cpf;
    private final LocalDate dataNascimento;
}
