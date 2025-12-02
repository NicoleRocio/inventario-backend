package com.plataforma.plataforma.repository;

import com.plataforma.plataforma.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Pedidos por usuario (lo que ya tenías)
    List<Pedido> findByUsuarioId(Long usuarioId);

    // 🆕 Pedidos por usuario y estado (para MisProductosAsignados)
    List<Pedido> findByUsuarioIdAndEstado(Long usuarioId, String estado);
}
