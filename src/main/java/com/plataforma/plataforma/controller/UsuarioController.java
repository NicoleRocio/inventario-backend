package com.plataforma.plataforma.controller;

import com.plataforma.plataforma.dto.LoginRequest;
import com.plataforma.plataforma.model.Usuario;
import com.plataforma.plataforma.repository.UsuarioRepository;
// --- IMPORTS NUEVOS ---
import com.plataforma.plataforma.repository.PedidoRepository;
import com.plataforma.plataforma.repository.IncidenciaRepository;
import java.util.Map;
import java.util.HashMap;
// ----------------------

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- INYECCIÓN DE REPOSITORIOS NUEVOS ---
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private IncidenciaRepository incidenciaRepository;
    // ----------------------------------------

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setUsername(usuarioDetails.getUsername());
            usuario.setPassword(usuarioDetails.getPassword());
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setEmpleado(usuarioDetails.getEmpleado());
            return ResponseEntity.ok(usuarioRepository.save(usuario));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuarioRepository.delete(usuario);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Faltan credenciales");
        }
        return usuarioRepository.findByUsername(loginRequest.getUsername())
                .map(usuario -> {
                    if (usuario.getPassword().equals(loginRequest.getPassword())) {
                        return ResponseEntity.ok(usuario);
                    } else {
                        return ResponseEntity.status(401).body("Contraseña incorrecta");
                    }
                })
                .orElse(ResponseEntity.status(404).body("Usuario no encontrado"));
    }

    // ==========================================
    // 🟢 NUEVO: ENDPOINT PARA EL DASHBOARD
    // ==========================================
    @GetMapping("/{id}/dashboard")
    public ResponseEntity<Map<String, Long>> getDashboardSummary(@PathVariable Long id) {
        // 1. Validar que el usuario exista
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // 2. Obtener las estadísticas usando tus repositorios

        // A. Total de solicitudes (Cualquier estado)
        long totalSolicitudes = pedidoRepository.countByUsuarioId(id);

        // B. Total de incidencias
        long totalIncidencias = incidenciaRepository.countByUsuarioId(id);

        // C. Mis Activos (Solo los pedidos completados/entregados)
        // ⚠️ IMPORTANTE: Asegúrate de que "ENTREGADO" sea la palabra exacta en tu Base de Datos.
        // Si usas "APROBADO" o "ASIGNADO", cambia la palabra aquí abajo.
        long totalActivos = pedidoRepository.countByUsuarioIdAndEstado(id, "Atendido");

        // 3. Armar la respuesta JSON
        Map<String, Long> response = new HashMap<>();
        response.put("totalActivos", totalActivos);
        response.put("totalIncidencias", totalIncidencias);
        response.put("totalSolicitudes", totalSolicitudes);

        return ResponseEntity.ok(response);
    }
}