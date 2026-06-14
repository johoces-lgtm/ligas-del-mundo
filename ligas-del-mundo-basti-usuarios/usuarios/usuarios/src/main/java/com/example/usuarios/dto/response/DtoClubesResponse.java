package com.example.usuarios.dto.response;

// IMPORTACIÓN DE SWAGGER
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO enviado por el microservicio de clubes con la información detallada del equipo")
public class DtoClubesResponse {

    @Schema(description = "Identificador único del club", example = "12")
    private Long id;

    @Schema(description = "Nombre oficial del club de fútbol", example = "Real Madrid")
    private String nombre;

    @Schema(description = "Enlace directo a la imagen del escudo del club", example = "http://imagenes.com/logos/realmadrid.png")
    private String logoUrl;

    @Schema(description = "Año en el que se fundó la institución", example = "1902")
    private Integer anioFundacion;

    @Schema(description = "Nombre oficial del estadio del club", example = "Santiago Bernabéu")
    private String estadioNombre;

    @Schema(description = "ID de la liga a la que pertenece el club", example = "3")
    private Long ligaId;
}