package com.plataforma.plataforma.repository;

import com.plataforma.plataforma.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // NUEVO: Obtener pedidos por ID del usuario
    List<Pedido> findByUsuarioId(Long usuarioId);
}
