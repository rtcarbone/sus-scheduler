package br.com.fiap.sus_scheduler.infrastructure.web.controller;

import br.com.fiap.sus_scheduler.application.usecase.*;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.PacienteRequest;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.PacienteResponse;
import br.com.fiap.sus_scheduler.infrastructure.web.mapper.WebMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(PacienteController.class)
class PacienteControllerTest {

    @MockBean
    CriarPacienteUseCase criar;
    @MockBean
    BuscarPacientePorIdUseCase buscarPorId;
    @MockBean
    ListarPacientesUseCase listar;
    @MockBean
    AtualizarPacienteUseCase atualizar;
    @MockBean
    ExcluirPacienteUseCase excluir;
    @Resource
    private MockMvc mvc;
    @Resource
    private ObjectMapper om;
    @MockBean
    private WebMapper mapper;

    @Test
    void deveCriarPaciente() throws Exception {
        var req = new PacienteRequest("Maria", "123.456.789-00", LocalDate.parse("1970-01-01"));
        var dominio = Paciente.builder()
                .id(UUID.randomUUID())
                .nome("Maria")
                .cpf("123.456.789-00")
                .dataNascimento(LocalDate.parse("1970-01-01"))
                .build();
        var resp = new PacienteResponse(dominio.getId(), dominio.getNome(), dominio.getCpf(), dominio.getDataNascimento());

        Mockito.when(mapper.toDomain(any(PacienteRequest.class)))
                .thenReturn(dominio);
        Mockito.when(criar.executar(any()))
                .thenReturn(dominio);
        Mockito.when(mapper.toResponse(any(Paciente.class)))
                .thenReturn(resp);

        mvc.perform(post("/pacientes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dominio.getId()
                                                          .toString()))
                .andExpect(jsonPath("$.nome").value("Maria"));
    }
}