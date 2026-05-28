package com.example.auth.service;

import java.security.Key;
import java.util.Date;
<<<<<<< HEAD
=======

import org.springframework.beans.factory.annotation.Value;
>>>>>>> 0176589b97d406ff537a2beaaede876753e24499
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

<<<<<<< HEAD
    private static final String SECRET = "miclavesupersecretaparajwt123456789";

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
=======
    @Value("${jwt.secret}")
    private String secretKey;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
>>>>>>> 0176589b97d406ff537a2beaaede876753e24499
    }

    public String generarToken(String correo) {
        return Jwts.builder()
                .setSubject(correo)
                .setIssuedAt(new Date())
<<<<<<< HEAD
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
=======
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora de validez
>>>>>>> 0176589b97d406ff537a2beaaede876753e24499
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}