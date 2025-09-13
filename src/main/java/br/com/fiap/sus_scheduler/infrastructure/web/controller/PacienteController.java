package br.com.fiap.sus_scheduler.infrastructure.web.controller;

import br.com.fiap.sus_scheduler.application.usecase.*;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.PacienteRequest;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.PacienteResponse;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.PacienteUpdateRequest;
import br.com.fiap.sus_scheduler.infrastructure.web.mapper.WebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {
    private final CriarPacienteUseCase criar;
    private final BuscarPacientePorIdUseCase buscarPorId;
    private final ListarPacientesUseCase listar;
    private final AtualizarPacienteUseCase atualizar;
    private final ExcluirPacienteUseCase excluir;
    private final WebMapper mapper;

    @PostMapping
    public ResponseEntity<PacienteResponse> criar(@RequestBody @Valid PacienteRequest req) {
        Paciente salvo = criar.executar(mapper.toDomain(req));
        return ResponseEntity.ok(mapper.toResponse(salvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscar(@PathVariable UUID id) {
        Paciente paciente = buscarPorId.executar(id);
        return ResponseEntity.ok(mapper.toResponse(paciente));
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listar(
            @RequestParam(required = false) String cpf
    ) {
        var lista = listar.executar(cpf)
                .stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> atualizar(@PathVariable UUID id,
                                                      @RequestBody @Valid PacienteUpdateRequest req) {
        var paciente = atualizar.executar(id, req.nome(), req.cpf(), req.dataNascimento());
        return ResponseEntity.ok(mapper.toResponse(paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        excluir.executar(id);
        return ResponseEntity.noContent()
                .build();
    }
}
