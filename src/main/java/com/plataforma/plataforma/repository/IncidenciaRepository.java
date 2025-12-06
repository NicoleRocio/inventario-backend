package com.plataforma.plataforma.repository;

import com.plataforma.plataforma.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {

    // Este ya lo tenías (Sirve para ver la lista completa en "Mis Incidencias")
    List<Incidencia> findByUsuarioId(Long usuarioId);

    // --- AGREGA ESTA LÍNEA ---
    // Sirve para la tarjeta del Dashboard (Solo devuelve el número, ej: 5)
    long countByUsuarioId(Long usuarioId);
}