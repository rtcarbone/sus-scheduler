package br.com.fiap.sus_scheduler.infrastructure.web.controller;

import br.com.fiap.sus_scheduler.infrastructure.web.dto.PacienteRequest;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.PacienteResponse;
import br.com.fiap.sus_scheduler.infrastructure.web.mapper.WebMapper;
import br.com.fiap.sus_scheduler.application.usecases.CriarPacienteUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {
    private final CriarPacienteUseCase criar;
    private final WebMapper mapper;

    @PostMapping
    public ResponseEntity<PacienteResponse> criar(@RequestBody @Valid PacienteRequest req) {
        var salvo = criar.executar(mapper.toDomain(req));
        return ResponseEntity.ok(new PacienteResponse(salvo.getId(), salvo.getNome(), salvo.getCpf(), salvo.getDataNascimento()));
    }
}
