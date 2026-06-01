package com.example.usuarios.service;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.usuarios.dto.request.UsuarioRequestDto;
import com.example.usuarios.model.Usuario;
import com.example.usuarios.repository.UsuarioRepository;
import com.example.usuarios.exception.ResourceNotFoundException;
import com.example.usuarios.client.ClubesClient;
import com.example.usuarios.dto.response.DtoClubesResponse;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final ClubesClient clubesClient;

    public UsuarioService(UsuarioRepository repository, ClubesClient clubesClient) {
        this.repository = repository;
        this.clubesClient = clubesClient;
        this.encoder = new BCryptPasswordEncoder();
    }

    public List<Usuario> listar() {
        clubesClient.pingClubes();
        return repository.findAll();
    }

    public Usuario buscar(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        if (clubesClient.obtenerClubPorId(usuario.getClubFavoritoId()) == null) {
            throw new ResourceNotFoundException("Inconsistencia detectada: El Club Favorito de este usuario ya no existe en el sistema padre.");
        }
        return usuario;
    }

    public Usuario guardar(UsuarioRequestDto dto) {
        DtoClubesResponse club = clubesClient.obtenerClubPorId(dto.getClubFavoritoId());
        if (club == null) {
            throw new ResourceNotFoundException("No se puede registrar el usuario. El Club Favorito con ID: " 
                    + dto.getClubFavoritoId() + " no existe en el sistema de Clubes.");
        }

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
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. Usuario no encontrado con ID: " + id));

        DtoClubesResponse club = clubesClient.obtenerClubPorId(dto.getClubFavoritoId());
        if (club == null) {
            throw new ResourceNotFoundException("No se puede actualizar el usuario. El Club Favorito con ID: " 
                    + dto.getClubFavoritoId() + " no existe en el sistema de Clubes.");
        }

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(encoder.encode(dto.getPassword()));
        usuario.setRol(dto.getRol());
        usuario.setClubFavoritoId(dto.getClubFavoritoId());

        return repository.save(usuario);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Usuario no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    public Usuario buscarPorCorreo(String correo) {
        Usuario usuario = repository.findByCorreo(correo);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado con correo: " + correo);
        }
        return usuario;
    }
}