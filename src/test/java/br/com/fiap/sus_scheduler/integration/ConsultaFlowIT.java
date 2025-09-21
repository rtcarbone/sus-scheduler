package br.com.fiap.sus_scheduler.integration;

import br.com.fiap.sus_scheduler.SusSchedulerApplication;
import br.com.fiap.sus_scheduler.config.PostgresTestContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integração ponta-a-ponta:
 * - cria paciente
 * - cria médico
 * - agenda consulta
 * - lista consultas por paciente
 */
@SpringBootTest(classes = SusSchedulerApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ConsultaFlowIT extends PostgresTestContainer {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    private UUID pacienteId;
    private UUID medicoCardioId;

    @BeforeEach
    void seed() throws Exception {
        // cria paciente
        var pac = Map.of("nome", "Maria", "cpf", "123.456.789-00", "dataNascimento", LocalDate.parse("1970-01-01"));
        var pacResp = mvc.perform(post("/pacientes").contentType(MediaType.APPLICATION_JSON)
                                          .content(om.writeValueAsString(pac)))
                .andExpect(status().isOk())
                .andReturn();
        pacienteId = UUID.fromString(om.readTree(pacResp.getResponse()
                                                         .getContentAsString())
                                             .get("id")
                                             .asText());

        // cria medico cardiologista
        var med = Map.of("nome", "Dr. Carlos", "especialidade", "CARDIOLOGIA", "unidade", "UBS Centro");
        var medResp = mvc.perform(post("/medicos").contentType(MediaType.APPLICATION_JSON)
                                          .content(om.writeValueAsString(med)))
                .andExpect(status().isOk())
                .andReturn();
        medicoCardioId = UUID.fromString(om.readTree(medResp.getResponse()
                                                             .getContentAsString())
                                                 .get("id")
                                                 .asText());
    }

    @Test
    void deveAgendarEListarConsultaPorPaciente() throws Exception {
        // agenda consulta (CARDIOLOGIA, urgência = false)
        var agendar = Map.of("pacienteId", pacienteId.toString(), "especialidade", "CARDIOLOGIA", "urgencia", false);
        mvc.perform(post("/consultas").contentType(MediaType.APPLICATION_JSON)
                            .content(om.writeValueAsString(agendar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.especialidade").value("CARDIOLOGIA"))
                .andExpect(jsonPath("$.status").value("AGENDADA"));

        // lista por paciente
        mvc.perform(get("/consultas").param("pacienteId", pacienteId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pacienteId").value(pacienteId.toString()));
    }
}