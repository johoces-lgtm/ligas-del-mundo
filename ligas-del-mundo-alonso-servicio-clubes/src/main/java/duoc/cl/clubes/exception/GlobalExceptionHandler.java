package duoc.cl.clubes.exception;

import java.time.LocalDate;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import duoc.cl.clubes.dto.DtoApiError;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DtoApiError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        DtoApiError error = DtoApiError.builder()
                .timestamp(LocalDate.now()) // El profe pidió LocalDate
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .claseException(ex.getClass().getSimpleName())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
