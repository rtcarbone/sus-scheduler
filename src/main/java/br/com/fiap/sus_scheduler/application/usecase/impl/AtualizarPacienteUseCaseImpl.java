package br.com.fiap.sus_scheduler.application.usecase.impl;

import br.com.fiap.sus_scheduler.application.gateway.PacienteGateway;
import br.com.fiap.sus_scheduler.application.usecase.AtualizarPacienteUseCase;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AtualizarPacienteUseCaseImpl implements AtualizarPacienteUseCase {
    private final PacienteGateway gateway;

    @Override
    public Paciente executar(UUID id, String nome, String cpf, LocalDate dataNascimento) {
        var atual = gateway.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));

        if (cpf != null && !cpf.equals(atual.getCpf())) {
            var existente = gateway.buscarPorCpf(cpf);
            if (existente.isPresent() && !existente.get()
                    .getId()
                    .equals(id)) {
                throw new IllegalArgumentException("Já existe outro paciente com o CPF informado");
            }
        }

        var atualizado = Paciente.builder()
                .id(atual.getId())
                .nome(nome != null ? nome : atual.getNome())
                .cpf(cpf != null ? cpf : atual.getCpf())
                .dataNascimento(dataNascimento != null ? dataNascimento : atual.getDataNascimento())
                .build();

        return gateway.salvar(atualizado);
    }
}
