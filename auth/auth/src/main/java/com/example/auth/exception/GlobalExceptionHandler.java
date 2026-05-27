package main.java.com.example.auth.exception;

import java.time.LocalDate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.auth.dto.response.DtoApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<DtoApiError> handleUnauthorized(BadCredentialsException ex, HttpServletRequest request){
        DtoApiError error = DtoApiError.builder()
            .timestamp(LocalDate.now())
            .status(HttpStatus.UNAUTHORIZED.value())
            .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .claseException(ex.getClass().getSimpleName())
            .build();
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}