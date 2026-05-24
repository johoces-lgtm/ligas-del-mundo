package com.example.usuarios.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usuarios.dto.UsuarioRequestDto;
import com.example.usuarios.model.Usuario;
import com.example.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository) {

        this.repository = repository;
        this.encoder=new BCryptPasswordEncoder();
    }

    public List<Usuario> listar() {

        return repository.findAll();
    }

    public Usuario buscar(Long id) {

        return repository.findById(id).orElse(null);
    }

    public Usuario guardar(UsuarioRequestDto dto) {

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .password(encoder.encode(dto.getPassword()))
                .rol(dto.getRol())
                .clubFavoritoId(dto.getClubFavoritoId())
                .build();

        return repository.save(usuario);
    }

    public Usuario actualizar(Long id, UsuarioRequestDto dto) {

        Usuario usuario = repository.findById(id).orElse(null);

        if (usuario != null) {

            usuario.setNombre(dto.getNombre());
            usuario.setCorreo(dto.getCorreo());
            usuario.setPassword(encoder.encode(dto.getPassword()));
            usuario.setRol(dto.getRol());
            usuario.setClubFavoritoId(dto.getClubFavoritoId());

            return repository.save(usuario);
        }

        return null;
    }

    public void eliminar(Long id) {

        repository.deleteById(id);
    }

    public Usuario buscarPorCorreo(String correo) {
        return repository.findByCorreo(correo);
    }
}