package com.example.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
<<<<<<< HEAD
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
=======
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
>>>>>>> 0176589b97d406ff537a2beaaede876753e24499
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
<<<<<<< HEAD
    public BCryptPasswordEncoder passwordEncoder() {
=======
    public PasswordEncoder passwordEncoder() {
>>>>>>> 0176589b97d406ff537a2beaaede876753e24499
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
<<<<<<< HEAD
                .requestMatchers("/api/auth/login").permitAll()
=======
                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
>>>>>>> 0176589b97d406ff537a2beaaede876753e24499
                .anyRequest().authenticated()
            )
            .build();
    }
}