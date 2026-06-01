package com.example.posiciones.dto.response;

import lombok.Data;

@Data
public class DtoClubesResponse {
    private Long id;
    private String nombre;
    private String logoUrl;
    private Integer anioFundacion;
    private String estadioNombre;
    private Long ligaId;
}