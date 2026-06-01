package com.example.partidos.dto.response;

import lombok.Data;

@Data
public class ClubDto {
    
    private Long id;
    private String nombre;
    private String logoUrl;
    private Integer anioFundacion;
    private String estadioNombre;
    private Long ligaId;
}