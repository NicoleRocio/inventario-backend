package com.plataforma.plataforma.dto;

public class IncidenciaRequestDTO {

    private Long usuarioId;
    private Long productoId;
    private String nombreProducto;
    private String imagenProducto;

    private String area;
    private String descripcion;

    // Getters y Setters

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getImagenProducto() { return imagenProducto; }
    public void setImagenProducto(String imagenProducto) { this.imagenProducto = imagenProducto; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
