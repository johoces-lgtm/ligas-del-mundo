package duoc.cl.paises.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

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