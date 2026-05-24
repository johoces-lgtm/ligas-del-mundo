package com.example.posiciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.posiciones.model.Posicion;

@Repository
public interface PosicionRepository extends JpaRepository<Posicion, Long> {
}