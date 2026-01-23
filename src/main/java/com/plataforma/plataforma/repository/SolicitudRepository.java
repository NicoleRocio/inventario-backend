package com.plataforma.plataforma.repository;

import com.plataforma.plataforma.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    // método para buscar todas las solicitudes de un usuario específico
    List<Solicitud> findByUsuarioId(Long usuarioId);
}
