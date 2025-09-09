package br.com.fiap.sus_scheduler.domain.entity;

import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Medico {
    private final UUID id;
    private final String nome;
    private final Especialidade especialidade;
    private final String unidade;
}