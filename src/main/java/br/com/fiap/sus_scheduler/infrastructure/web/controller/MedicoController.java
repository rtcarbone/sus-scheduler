package br.com.fiap.sus_scheduler.infrastructure.web.controller;

import br.com.fiap.sus_scheduler.application.usecase.*;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.MedicoRequest;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.MedicoResponse;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.MedicoUpdateRequest;
import br.com.fiap.sus_scheduler.infrastructure.web.mapper.WebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {
    private final CriarMedicoUseCase criar;
    private final BuscarMedicoPorIdUseCase buscarPorId;
    private final ListarMedicosUseCase listar;
    private final AtualizarMedicoUseCase atualizar;
    private final ExcluirMedicoUseCase excluir;
    private final WebMapper mapper;

    @PostMapping
    public ResponseEntity<MedicoResponse> criar(@RequestBody @Valid MedicoRequest req) {
        var salvo = criar.executar(mapper.toDomain(req));
        return ResponseEntity.ok(mapper.toResponse(salvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponse> buscar(@PathVariable UUID id) {
        Medico medico = buscarPorId.executar(id);
        return ResponseEntity.ok(mapper.toResponse(medico));
    }

    @GetMapping
    public ResponseEntity<List<MedicoResponse>> listar(
            @RequestParam(required = false) Especialidade especialidade
    ) {
        var lista = listar.executar(especialidade)
                .stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponse> atualizar(@PathVariable UUID id,
                                                    @RequestBody @Valid MedicoUpdateRequest req) {
        Medico medico = atualizar.executar(id, req.nome(), req.especialidade(), req.unidade());
        return ResponseEntity.ok(mapper.toResponse(medico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        excluir.executar(id);
        return ResponseEntity.noContent()
                .build();
    }
}