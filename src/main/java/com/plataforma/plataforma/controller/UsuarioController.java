package com.plataforma.plataforma.controller;

import com.plataforma.plataforma.dto.LoginRequest;
import com.plataforma.plataforma.dto.UsuarioDTO;
import com.plataforma.plataforma.dto.UsuarioRegistroDTO; // <--- 1. NUEVO IMPORT
import com.plataforma.plataforma.model.Usuario;
import com.plataforma.plataforma.model.Rol;            // <--- 2. NUEVO IMPORT
import com.plataforma.plataforma.repository.UsuarioRepository;
import com.plataforma.plataforma.repository.PedidoRepository;
import com.plataforma.plataforma.repository.IncidenciaRepository;
import com.plataforma.plataforma.repository.RolRepository; // <--- 3. NUEVO IMPORT

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList; // <--- 4. IMPORTANTE para listas
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    // 👇 5. INYECTAMOS EL REPOSITORIO DE ROLES
    @Autowired
    private RolRepository rolRepository;

    // ======================================================================
    // 🆕 ENDPOINT 1: OBTENER LISTA DE ROLES (Para el select del frontend)
    // ======================================================================
    @GetMapping("/roles")
    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    // ======================================================================
    // 🆕 ENDPOINT 2: REGISTRO PROFESIONAL (Con asignación de Roles)
    // ======================================================================
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioRegistroDTO registroDTO) {

        // A. Validar si el usuario ya existe
        if (usuarioRepository.findByUsername(registroDTO.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe");
        }

        // B. Crear la entidad Usuario con los datos básicos
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(registroDTO.getUsername());
        nuevoUsuario.setPassword(registroDTO.getPassword()); // (Recuerda: idealmente encriptar aquí)
        nuevoUsuario.setNombre(registroDTO.getNombre());

        // C. Buscar los roles en la BD y asignarlos
        List<Rol> rolesAsignados = new ArrayList<>();

        if (registroDTO.getRolesIds() != null && !registroDTO.getRolesIds().isEmpty()) {
            for (Long rolId : registroDTO.getRolesIds()) {
                // Buscamos cada ID (ej: 3 para SOPORTE) en la base de datos
                rolRepository.findById(rolId).ifPresent(rol -> rolesAsignados.add(rol));
            }
        }

        nuevoUsuario.setRoles(rolesAsignados);

        // D. Guardar en Base de Datos
        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.ok("Usuario registrado con éxito");
    }
    // ======================================================================


    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // MÉTODO ACTUALIZADO: USA EL DTO (LO QUE YA TENÍAS)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    List<String> rolesNombres = usuario.getRoles().stream()
                            .map(rol -> rol.getNombre())
                            .collect(Collectors.toList());

                    UsuarioDTO dto = new UsuarioDTO(
                            usuario.getId(),
                            usuario.getUsername(),
                            usuario.getNombre(),
                            rolesNombres
                    );

                    return ResponseEntity.ok(dto);
                })
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

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<Map<String, Long>> getDashboardSummary(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        long totalSolicitudes = pedidoRepository.countByUsuarioId(id);
        long totalIncidencias = incidenciaRepository.countByUsuarioId(id);
        long totalActivos = pedidoRepository.countByUsuarioIdAndEstado(id, "Atendido");

        Map<String, Long> response = new HashMap<>();
        response.put("totalActivos", totalActivos);
        response.put("totalIncidencias", totalIncidencias);
        response.put("totalSolicitudes", totalSolicitudes);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@PathVariable Long id, @RequestBody Map<String, String> passwords) {
        String currentPassword = passwords.get("currentPassword");
        String newPassword = passwords.get("newPassword");

        return usuarioRepository.findById(id).map(usuario -> {
            if (!usuario.getPassword().equals(currentPassword)) {
                return ResponseEntity.status(401).body("La contraseña actual es incorrecta");
            }
            usuario.setPassword(newPassword);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Contraseña actualizada correctamente");
        }).orElse(ResponseEntity.notFound().build());
    }
}