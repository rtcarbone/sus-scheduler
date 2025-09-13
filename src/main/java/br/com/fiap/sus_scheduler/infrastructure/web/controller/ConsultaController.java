package br.com.fiap.sus_scheduler.infrastructure.web.controller;

import br.com.fiap.sus_scheduler.application.usecase.AgendarConsultaUseCase;
import br.com.fiap.sus_scheduler.application.usecase.CancelarConsultaUseCase;
import br.com.fiap.sus_scheduler.application.usecase.ListarConsultasPorPacienteUseCase;
import br.com.fiap.sus_scheduler.domain.entity.Consulta;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.AgendarConsultaRequest;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.ConsultaResponse;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.ErrorResponse;
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

@Tag(name = "Consultas", description = "Agendamento e gerenciamento de consultas")
@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final AgendarConsultaUseCase agendar;
    private final ListarConsultasPorPacienteUseCase listar;
    private final CancelarConsultaUseCase cancelar;
    private final WebMapper mapper;

    @Operation(summary = "Agenda uma nova consulta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta agendada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Paciente ou médico não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ConsultaResponse> agendar(
            @RequestBody @Valid AgendarConsultaRequest req) {
        Consulta consultaSalva = agendar.executar(req.pacienteId(), req.especialidade(), req.urgencia());
        return ResponseEntity.ok(mapper.toResponse(consultaSalva));
    }

    @Operation(summary = "Lista consultas de um paciente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> porPaciente(
            @Parameter(description = "ID do paciente") @RequestParam UUID pacienteId) {
        var lista = listar.executar(pacienteId)
                .stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Cancela uma consulta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta cancelada"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ConsultaResponse> cancelar(
            @Parameter(description = "ID da consulta") @PathVariable UUID id) {
        Consulta consulta = cancelar.executar(id);
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }
}
