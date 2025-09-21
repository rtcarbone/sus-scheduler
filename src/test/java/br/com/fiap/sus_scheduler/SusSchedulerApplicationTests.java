package br.com.fiap.sus_scheduler;

import br.com.fiap.sus_scheduler.config.PostgresTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SusSchedulerApplicationTests extends PostgresTestContainer {

    @Test
    void contextLoads() {
    }

}
