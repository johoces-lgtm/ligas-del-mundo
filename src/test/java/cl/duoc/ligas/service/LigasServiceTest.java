package cl.duoc.ligas.service;

import cl.duoc.ligas.dto.request.DtoLigasRequest;
import cl.duoc.ligas.exception.ResourceNotFoundException;
import cl.duoc.ligas.repository.LigasRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LigasServiceTest {

    @Mock
    private LigasRepository ligasrepository;

    @InjectMocks
    private LigasService ligasService;

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException al actualizar una liga inexistente")
    void actualizarLiga_CuandoLigaNoExiste_LanzaExcepcion() {
        Long idInexistente = 999L;
        DtoLigasRequest requestActualizacion = new DtoLigasRequest(); 
        requestActualizacion.setNombre("La Liga Modificada");

        when(ligasrepository.findById(idInexistente)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            ligasService.actualizarLiga(idInexistente, requestActualizacion);
        });

        assertEquals("Liga no encontrada con ID: 999", exception.getMessage());
        verify(ligasrepository, never()).save(any());
    }
}