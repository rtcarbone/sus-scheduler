package br.com.fiap.sus_scheduler.infrastructure.web.config;

import br.com.fiap.sus_scheduler.domain.exception.*;
import br.com.fiap.sus_scheduler.infrastructure.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private ErrorResponse buildError(HttpStatus status, String message) {
        return new ErrorResponse(
                OffsetDateTime.now()
                        .toString(),
                status.value(),
                status.getReasonPhrase(),
                message
        );
    }

    @ExceptionHandler({
            PacienteNotFoundException.class,
            MedicoNotFoundException.class,
            ConsultaNotFoundException.class,
            MedicoNaoDisponivelException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(CpfDuplicadoException.class)
    public ResponseEntity<ErrorResponse> handleConflict(CpfDuplicadoException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildError(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

}
