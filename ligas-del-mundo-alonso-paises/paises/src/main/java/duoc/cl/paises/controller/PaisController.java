package duoc.cl.paises.controller;

import duoc.cl.paises.dto.request.DtoPaisRequest;
import duoc.cl.paises.dto.response.DtoPaisResponse;
import duoc.cl.paises.service.PaisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/paises")
@RequiredArgsConstructor
public class PaisController {

    private final PaisService paisService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DtoPaisResponse crear(@RequestBody @Valid DtoPaisRequest request) {
        return paisService.crearPais(request);
    }

    @GetMapping("/{id}")
    public DtoPaisResponse obtenerPorId(@PathVariable Long id) {
        return paisService.obtenerPaisPorId(id);
    }

    @GetMapping
    public List<DtoPaisResponse> listarTodos() {
        return paisService.obtenerTodos();
    }
}