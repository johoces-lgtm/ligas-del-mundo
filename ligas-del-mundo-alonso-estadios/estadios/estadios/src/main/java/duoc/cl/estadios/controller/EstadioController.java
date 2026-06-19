package duoc.cl.estadios.controller;

import duoc.cl.estadios.dto.response.DtoEstadioResponse;
import duoc.cl.estadios.dto.request.DtoEstadioRequest;
import duoc.cl.estadios.service.EstadioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estadios")
@RequiredArgsConstructor
public class EstadioController {

    private final EstadioService estadioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DtoEstadioResponse crear(@RequestBody @Valid DtoEstadioRequest request) {
        return estadioService.crearEstadio(request);
    }

    @GetMapping("/{id}")
    public DtoEstadioResponse obtenerPorId(@PathVariable Long id) {
        return estadioService.obtenerEstadioPorId(id);
    }

    @GetMapping
    public List<DtoEstadioResponse> listarTodos() {
        return estadioService.obtenerTodos();
    }
}