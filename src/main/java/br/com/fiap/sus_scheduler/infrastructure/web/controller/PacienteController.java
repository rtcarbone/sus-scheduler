package br.com.fiap.sus_scheduler.infrastructure.web.controller;

import br.com.fiap.sus_scheduler.application.usecase.*;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.ErrorResponse;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.PacienteRequest;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.PacienteResponse;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.PacienteUpdateRequest;
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

@Tag(name = "Pacientes", description = "CRUD de pacientes")
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

    @Operation(summary = "Cria paciente", description = "Cria um novo paciente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "CPF já existente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<PacienteResponse> criar(@RequestBody @Valid PacienteRequest req) {
        Paciente salvo = criar.executar(mapper.toDomain(req));
        return ResponseEntity.ok(mapper.toResponse(salvo));
    }

    @Operation(summary = "Busca paciente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscar(
            @Parameter(description = "ID do paciente") @PathVariable UUID id) {
        Paciente paciente = buscarPorId.executar(id);
        return ResponseEntity.ok(mapper.toResponse(paciente));
    }

    @Operation(summary = "Lista pacientes", description = "Lista pacientes com filtro opcional por CPF")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listar(
            @Parameter(description = "Filtro por CPF (opcional)") @RequestParam(required = false) String cpf) {
        var lista = listar.executar(cpf)
                .stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Atualiza paciente", description = "Atualiza dados do paciente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "CPF já existente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> atualizar(
            @Parameter(description = "ID do paciente") @PathVariable UUID id,
            @RequestBody @Valid PacienteUpdateRequest req) {
        var paciente = atualizar.executar(id, req.nome(), req.cpf(), req.dataNascimento());
        return ResponseEntity.ok(mapper.toResponse(paciente));
    }

    @Operation(summary = "Remove paciente", description = "Exclui um paciente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Paciente removido"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do paciente") @PathVariable UUID id) {
        excluir.executar(id);
        return ResponseEntity.noContent()
                .build();
    }
}
