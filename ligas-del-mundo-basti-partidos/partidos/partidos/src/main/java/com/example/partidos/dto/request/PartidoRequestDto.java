package com.example.partidos.dto.request;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Modelo de petición para agendar o actualizar un partido")
public class PartidoRequestDto {

    @NotNull
    @Schema(description = "ID de la liga en la que se compite", example = "3")
    private Long ligaId;

    @NotNull
    @Schema(description = "ID del club local", example = "1")
    private Long clubLocalId;

    @NotNull
    @Schema(description = "ID del club visitante", example = "2")
    private Long clubVisitaId;

    @NotNull
    @Schema(description = "ID del estadio donde se juega el encuentro", example = "5")
    private Long estadioId;

    @Schema(description = "Nombre oficial del equipo de casa", example = "Real Madrid")
    private String nombreLocal;

    @Schema(description = "Nombre oficial del equipo visitante", example = "Barcelona")
    private String nombreVisita;

    @Schema(description = "Goles anotados por el local", example = "3")
    private Integer golesLocal;

    @Schema(description = "Goles anotados por el visitante", example = "2")
    private Integer golesVisita;

    @Schema(description = "Estado del partido (PROGRAMADO, EN_CURSO, FINALIZADO)", example = "FINALIZADO")
    private String estado;

    @Schema(description = "Año correspondiente a la temporada", example = "2026")
    private Integer temporada;
}