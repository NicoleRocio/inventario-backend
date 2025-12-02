package com.plataforma.plataforma.controller;

import com.plataforma.plataforma.dto.IncidenciaRequestDTO;
import com.plataforma.plataforma.model.Incidencia;
import com.plataforma.plataforma.service.IncidenciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/incidencias")
@CrossOrigin(origins = "*")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    @PostMapping
    public Incidencia registrar(@RequestBody IncidenciaRequestDTO dto) {
        return incidenciaService.registrarIncidencia(dto);
    }

    @GetMapping("/usuario/{id}")
    public List<Incidencia> obtenerPorUsuario(@PathVariable Long id) {
        return incidenciaService.obtenerPorUsuario(id);
    }

    @GetMapping
    public List<Incidencia> listarTodas() {
        return incidenciaService.listarTodas();
    }

    @PutMapping("/{id}/estado")
    public Incidencia actualizarEstado(@PathVariable Long id,
                                       @RequestBody Map<String, String> body) {
        return incidenciaService.actualizarEstado(id, body.get("estado"));
    }
}
