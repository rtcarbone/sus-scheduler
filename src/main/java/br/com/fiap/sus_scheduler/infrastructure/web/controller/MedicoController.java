package br.com.fiap.sus_scheduler.infrastructure.web.controller;

import br.com.fiap.sus_scheduler.application.usecase.*;
import br.com.fiap.sus_scheduler.domain.entity.Medico;
import br.com.fiap.sus_scheduler.domain.enums.Especialidade;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.ErrorResponse;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.MedicoRequest;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.MedicoResponse;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.MedicoUpdateRequest;
import br.com.fiap.sus_scheduler.infrastructure.web.mapper.WebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Médicos", description = "CRUD de médicos")
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

    @Operation(summary = "Cria médico", description = "Cria um novo médico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Médico criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<MedicoResponse> criar(@RequestBody @Valid MedicoRequest req) {
        var salvo = criar.executar(mapper.toDomain(req));
        return ResponseEntity.ok(mapper.toResponse(salvo));
    }

    @Operation(summary = "Busca médico por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Médico encontrado"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponse> buscar(
            @Parameter(description = "ID do médico") @PathVariable UUID id) {
        Medico medico = buscarPorId.executar(id);
        return ResponseEntity.ok(mapper.toResponse(medico));
    }

    @Operation(summary = "Lista médicos", description = "Lista médicos com filtro opcional por especialidade")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<MedicoResponse>> listar(
            @Parameter(description = "Especialidade (opcional)")
            @RequestParam(required = false) Especialidade especialidade
    ) {
        var lista = listar.executar(especialidade)
                .stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Atualiza médico", description = "Atualiza dados do médico por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Médico atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponse> atualizar(
            @Parameter(description = "ID do médico") @PathVariable UUID id,
            @RequestBody @Valid MedicoUpdateRequest req) {
        Medico medico = atualizar.executar(id, req.nome(), req.especialidade(), req.unidade());
        return ResponseEntity.ok(mapper.toResponse(medico));
    }

    @Operation(summary = "Remove médico", description = "Exclui um médico por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Médico removido"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do médico") @PathVariable UUID id) {
        excluir.executar(id);
        return ResponseEntity.noContent()
                .build();
    }
}