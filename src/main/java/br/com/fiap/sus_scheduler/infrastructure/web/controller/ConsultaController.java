package br.com.fiap.sus_scheduler.infrastructure.web.controller;

import br.com.fiap.sus_scheduler.infrastructure.web.dto.AgendarConsultaRequest;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.ConsultaResponse;
import br.com.fiap.sus_scheduler.infrastructure.web.mapper.WebMapper;
import br.com.fiap.sus_scheduler.application.usecase.AgendarConsultaUseCase;
import br.com.fiap.sus_scheduler.application.usecase.CancelarConsultaUseCase;
import br.com.fiap.sus_scheduler.application.usecase.ListarConsultasPorPacienteUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {
    private final AgendarConsultaUseCase agendar;
    private final ListarConsultasPorPacienteUseCase listar;
    private final CancelarConsultaUseCase cancelar;
    private final WebMapper mapper;

    @PostMapping
    public ResponseEntity<ConsultaResponse> agendar(@RequestBody @Valid AgendarConsultaRequest req) {
        var c = agendar.executar(req.pacienteId(), req.especialidade(), req.urgencia(), req.idade());
        return ResponseEntity.ok(mapper.toResponse(c));
    }

    @GetMapping
    public ResponseEntity<?> porPaciente(@RequestParam UUID pacienteId) {
        var lista = listar.executar(pacienteId)
                .stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ConsultaResponse> cancelar(@PathVariable UUID id) {
        var c = cancelar.executar(id);
        return ResponseEntity.ok(mapper.toResponse(c));
    }
}
