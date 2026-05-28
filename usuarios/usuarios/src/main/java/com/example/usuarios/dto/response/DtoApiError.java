package main.java.com.example.usuarios.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoApiError {
    private LocalDate timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    private String claseException;
}