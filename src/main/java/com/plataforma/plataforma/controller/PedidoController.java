package com.plataforma.plataforma.controller;

import com.plataforma.plataforma.dto.ProductoAsignadoDTO;
import com.plataforma.plataforma.model.Pedido;
import com.plataforma.plataforma.repository.PedidoRepository;
import com.plataforma.plataforma.service.PedidoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final PedidoService pedidoService;

    public PedidoController(PedidoRepository pedidoRepository, PedidoService pedidoService) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoService = pedidoService;
    }

    // ✅ Obtener todos los pedidos (para el admin)
    @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    // ✅ Crear pedido
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        try {
            if (pedido.getDetalles() != null) {
                pedido.getDetalles().forEach(detalle -> detalle.setPedido(pedido));
            }
            Pedido pedidoGuardado = pedidoRepository.save(pedido);
            return ResponseEntity.ok(pedidoGuardado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ✅ Obtener pedido por ID
    @GetMapping("/{id}")
    public Pedido obtenerPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id " + id));
    }

    // ✅ Pedidos por usuario (MisPedidos)
    @GetMapping("/usuario/{id}")
    public List<Pedido> obtenerPorUsuario(@PathVariable Long id) {
        return pedidoRepository.findByUsuarioId(id);
    }

    // 🆕 Productos asignados (pedidos ATENDIDOS) para un usuario
    @GetMapping("/asignados/{usuarioId}")
    public List<ProductoAsignadoDTO> obtenerAsignados(@PathVariable Long usuarioId) {
        return pedidoService.getProductosAsignados(usuarioId);
    }

    // 🆕 ACTUALIZAR ESTADO DEL PEDIDO (En espera / Atendido)
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        return pedidoRepository.findById(id).map(pedido -> {
            String nuevoEstado = body.get("estado");
            if (nuevoEstado == null || nuevoEstado.isBlank()) {
                return ResponseEntity.badRequest().body("Estado no válido");
            }

            pedido.setEstado(nuevoEstado);
            pedidoRepository.save(pedido);

            return ResponseEntity.ok("Estado actualizado correctamente");
        }).orElse(ResponseEntity.notFound().build());
    }
}
