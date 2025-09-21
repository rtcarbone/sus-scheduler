package br.com.fiap.sus_scheduler.application.usecase;

import br.com.fiap.sus_scheduler.application.gateway.PacienteGateway;
import br.com.fiap.sus_scheduler.application.usecase.impl.AtualizarPacienteUseCaseImpl;
import br.com.fiap.sus_scheduler.domain.entity.Paciente;
import br.com.fiap.sus_scheduler.domain.exception.CpfDuplicadoException;
import br.com.fiap.sus_scheduler.domain.exception.PacienteNotFoundException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AtualizarPacienteUseCaseImplTest {

    private final PacienteGateway gateway = mock(PacienteGateway.class);
    private final AtualizarPacienteUseCaseImpl useCase = new AtualizarPacienteUseCaseImpl(gateway);

    @Test
    void deveAtualizarSomenteCamposInformados() {
        UUID id = UUID.randomUUID();
        var existente = Paciente.builder()
                .id(id)
                .nome("Maria")
                .cpf("123.456.789-00")
                .dataNascimento(LocalDate.parse("1970-01-01"))
                .build();

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(existente));
        when(gateway.salvar(any())).thenAnswer(a -> a.getArgument(0));

        var atualizado = useCase.executar(id, "Maria Silva", null, null);

        assertEquals("Maria Silva", atualizado.getNome());
        assertEquals("123.456.789-00", atualizado.getCpf());
        verify(gateway).salvar(any());
    }

    @Test
    void deveLancarSePacienteNaoExistir() {
        UUID id = UUID.randomUUID();
        when(gateway.buscarPorId(id)).thenReturn(Optional.empty());
        assertThrows(PacienteNotFoundException.class,
                     () -> useCase.executar(id, "X", null, null));
    }

    @Test
    void deveValidarCpfDuplicado() {
        UUID id = UUID.randomUUID();
        var atual = Paciente.builder()
                .id(id)
                .nome("A")
                .cpf("111.111.111-11")
                .dataNascimento(LocalDate.parse("1990-01-01"))
                .build();
        var outro = Paciente.builder()
                .id(UUID.randomUUID())
                .nome("B")
                .cpf("222.222.222-22")
                .dataNascimento(LocalDate.parse("1991-01-01"))
                .build();

        when(gateway.buscarPorId(id)).thenReturn(Optional.of(atual));
        when(gateway.buscarPorCpf("222.222.222-22")).thenReturn(Optional.of(outro));

        assertThrows(CpfDuplicadoException.class,
                     () -> useCase.executar(id, null, "222.222.222-22", null));
    }
}
