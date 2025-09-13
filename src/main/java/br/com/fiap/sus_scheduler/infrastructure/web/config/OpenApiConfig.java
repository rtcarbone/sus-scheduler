package br.com.fiap.sus_scheduler.infrastructure.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                              .title("SUS Scheduler API")
                              .description("API de agendamento de consultas — Clean Architecture")
                              .version("v1.0.0"))
                .servers(List.of(
                        new Server().url("http://localhost:8080")
                                .description("Ambiente Local")
                ));
    }
}
