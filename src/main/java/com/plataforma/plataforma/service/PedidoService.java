package com.plataforma.plataforma.service;

import com.plataforma.plataforma.dto.ProductoAsignadoDTO;
import com.plataforma.plataforma.model.DetallePedido;
import com.plataforma.plataforma.model.Pedido;
import com.plataforma.plataforma.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    // 🔵 Devuelve todos los productos de pedidos ATENDIDOS de un usuario
    public List<ProductoAsignadoDTO> getProductosAsignados(Long usuarioId) {

        List<Pedido> pedidosAtendidos =
                pedidoRepository.findByUsuarioIdAndEstado(usuarioId, "Atendido");

        List<ProductoAsignadoDTO> resultado = new ArrayList<>();

        for (Pedido pedido : pedidosAtendidos) {
            if (pedido.getDetalles() == null) continue;

            for (DetallePedido det : pedido.getDetalles()) {
                if (det.getProducto() == null) continue;

                resultado.add(new ProductoAsignadoDTO(
                        pedido.getId(),
                        pedido.getFecha(),
                        pedido.getEstado(),
                        det.getProducto().getId(),
                        det.getProducto().getNombre(),
                        det.getProducto().getImagen(),
                        det.getCantidad()
                ));
            }
        }

        return resultado;
    }
}
