package com.plataforma.plataforma.repository;

import com.plataforma.plataforma.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // --- MÉTODOS DE BÚSQUEDA (Devuelven listas para tablas) ---

    // Para ver "Mis Pedidos" (Historial completo)
    List<Pedido> findByUsuarioId(Long usuarioId);

    // Para ver "Mis Productos" (Solo los que ya tengo asignados)
    List<Pedido> findByUsuarioIdAndEstado(Long usuarioId, String estado);


    // --- MÉTODOS DE CONTEO (Devuelven números para el Dashboard) ---

    // 1. Cuenta TOTAL de solicitudes (para la tarjeta "Solicitudes")
    long countByUsuarioId(Long usuarioId);

    // 2. Cuenta solo los pedidos ACEPTADOS (para la tarjeta "Mis Activos")
    long countByUsuarioIdAndEstado(Long usuarioId, String estado);
}