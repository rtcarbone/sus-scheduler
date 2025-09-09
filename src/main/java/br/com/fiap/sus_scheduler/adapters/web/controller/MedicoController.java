package br.com.fiap.sus_scheduler.adapters.web.controller;

import br.com.fiap.sus_scheduler.adapters.web.dto.MedicoRequest;
import br.com.fiap.sus_scheduler.adapters.web.dto.MedicoResponse;
import br.com.fiap.sus_scheduler.adapters.web.mapper.WebMapper;
import br.com.fiap.sus_scheduler.application.usecases.CriarMedicoUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {
    private final CriarMedicoUseCase criar;
    private final WebMapper mapper;

    @PostMapping
    public ResponseEntity<MedicoResponse> criar(@RequestBody @Valid MedicoRequest req) {
        var salvo = criar.executar(mapper.toDomain(req));
        return ResponseEntity.ok(new MedicoResponse(salvo.getId(), salvo.getNome(), salvo.getEspecialidade(), salvo.getUnidade()));
    }
}