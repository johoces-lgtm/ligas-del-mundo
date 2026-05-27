package com.example.partidos.dto.response;

import lombok.Data;

@Data
public class LigaDto {
    
    private Long id;
    private String nombre;
    private String pais;
    private String logoUrl;
}