package com.plataforma.plataforma.dto;

import java.time.LocalDateTime;

public class ProductoAsignadoDTO {

    private Long pedidoId;
    private LocalDateTime fecha;
    private String estado;

    private Long productoId;
    private String nombreProducto;
    private String imagen;
    private int cantidad;

    public ProductoAsignadoDTO() {
    }

    public ProductoAsignadoDTO(Long pedidoId, LocalDateTime fecha, String estado,
                               Long productoId, String nombreProducto, String imagen, int cantidad) {
        this.pedidoId = pedidoId;
        this.fecha = fecha;
        this.estado = estado;
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.imagen = imagen;
        this.cantidad = cantidad;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
