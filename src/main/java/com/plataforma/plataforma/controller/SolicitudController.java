package com.plataforma.plataforma.controller;

import com.plataforma.plataforma.model.Solicitud;
import com.plataforma.plataforma.repository.SolicitudRepository;
import com.plataforma.plataforma.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuario/{usuarioId}")
    public List<Solicitud> getByUsuario(@PathVariable Long usuarioId) {
        return solicitudRepository.findByUsuarioId(usuarioId);
    }

    @PostMapping("/{usuarioId}")
    public ResponseEntity<?> crear(@PathVariable Long usuarioId, @RequestBody Solicitud solicitud) {
        return usuarioRepository.findById(usuarioId).map(usuario -> {

            // 1. Asignar datos básicos
            solicitud.setUsuario(usuario);
            solicitud.setEstado("Pendiente");
            solicitud.setFechaSolicitud(LocalDateTime.now()); // Marca de tiempo exacta

            // 2. CÁLCULOS INTELIGENTES 🧠
            if (Boolean.TRUE.equals(solicitud.getEsPorHoras())) {
                // Lógica para Horas
                if (solicitud.getHoraInicio() != null && solicitud.getHoraFin() != null) {
                    long minutos = ChronoUnit.MINUTES.between(solicitud.getHoraInicio(), solicitud.getHoraFin());
                    double horasDecimales = Math.round((minutos / 60.0) * 100.0) / 100.0; // Redondear a 2 decimales

                    solicitud.setTotalHoras(horasDecimales);
                    solicitud.setTotalDias(0);
                    // En permisos por horas, fecha fin es igual a fecha inicio
                    solicitud.setFechaFin(solicitud.getFechaInicio());
                }
            } else {
                // Lógica para Días
                if (solicitud.getFechaInicio() != null && solicitud.getFechaFin() != null) {
                    // +1 porque si pido del 20 al 20, es 1 día, no 0.
                    long dias = ChronoUnit.DAYS.between(solicitud.getFechaInicio(), solicitud.getFechaFin()) + 1;

                    solicitud.setTotalDias((int) dias);
                    solicitud.setTotalHoras(0.0);
                }
            }

            // 3. Guardar
            return ResponseEntity.ok(solicitudRepository.save(solicitud));
        }).orElse(ResponseEntity.notFound().build());
    }
}