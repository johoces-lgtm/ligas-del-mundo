package com.example.posiciones.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "posiciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clubId;

    @Column(nullable = false)
    private String nombreClub;

    @Column(nullable = false)
    private Integer puntos;

    @Column(nullable = false)
    private Integer partidosJugados;

    @Column(nullable = false)
    private Integer ganados;

    @Column(nullable = false)
    private Integer empatados;

    @Column(nullable = false)
    private Integer perdidos;

    @Column(nullable = false)
    private Integer golesFavor;

    @Column(nullable = false)
    private Integer golesContra;

    @Column(nullable = false)
    private Integer diferenciaGoles;

    @Column(nullable = false)
    private Integer temporada;

    @Column(nullable = false)
    private Long ligaId;
}