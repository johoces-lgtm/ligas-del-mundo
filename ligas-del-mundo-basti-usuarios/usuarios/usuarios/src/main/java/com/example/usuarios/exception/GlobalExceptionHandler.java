package com.example.usuarios.exception;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.usuarios.dto.DtoApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DtoApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request){
        DtoApiError error = DtoApiError.builder()
            .timestamp(LocalDate.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .claseException(ex.getClass().getSimpleName())
            .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(java.net.ConnectException.class)
    public ResponseEntity<DtoApiError> handleConnectionError(java.net.ConnectException ex, HttpServletRequest request) {
        DtoApiError error = DtoApiError.builder()
                .timestamp(LocalDate.now()) 
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .error(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                .message("El microservicio dependiente no está disponible o está apagado.")
                .path(request.getRequestURI())
                .claseException(ex.getClass().getSimpleName())
                .build();
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<DtoApiError> handleWebClientError(WebClientResponseException ex, HttpServletRequest request) {
        
        if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
            DtoApiError error = DtoApiError.builder()
                    .timestamp(LocalDate.now()) 
                    .status(HttpStatus.BAD_REQUEST.value()) 
                    .error("Validación de campos fallida")
                    .message("No se puede registrar el usuario. El Club Favorito con el ID indicado no existe en el sistema de Clubes.")
                    .path(request.getRequestURI())
                    .claseException("DataInconsistencyException")
                    .build();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        DtoApiError error = DtoApiError.builder()
                .timestamp(LocalDate.now()) 
                .status(ex.getStatusCode().value())
                .error("Error de comunicación entre microservicios")
                .message("El servicio externo respondió con error: " + ex.getStatusText())
                .path(request.getRequestURI())
                .claseException(ex.getClass().getSimpleName())
                .build();
        return new ResponseEntity<>(error, ex.getStatusCode());
    }
}