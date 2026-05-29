<<<<<<< HEAD:usuarios/usuarios/src/main/java/com/example/usuarios/dto/response/DtoApiError.java
package com.example.usuarios.dto.response;
=======
package com.example.usuarios.dto;
>>>>>>> 318f9ad505f268e644e483cdaea608ddadc0cb1a:usuarios/usuarios/src/main/java/com/example/usuarios/dto/DtoApiError.java

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