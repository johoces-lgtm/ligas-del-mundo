package com.example.partidos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "partido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long ligaId;

    @Column(nullable = false)
    private Long clubLocalId;

    @Column(nullable = false)
    private Long clubVisitaId;

    @Column(nullable = false)
    private Long estadioId;

    @Column(nullable = false)
    private String nombreLocal;

    @Column(nullable = false)
    private String nombreVisita;

    @Column(nullable = false)
    private Integer golesLocal;

    @Column(nullable = false)
    private Integer golesVisita;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private Integer temporada;
}