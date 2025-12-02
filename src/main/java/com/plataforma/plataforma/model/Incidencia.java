package com.plataforma.plataforma.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidencias")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String area;

    @Column(length = 1500)
    private String descripcion;

    private String estado = "Pendiente";

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime fecha;

    @JsonFormat(pattern = "HH:mm")
    private LocalDateTime hora;

    // Usuario que genera la incidencia
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Producto afectado
    private Long productoId;
    private String nombreProducto;
    private String imagenProducto;

    public Incidencia() {
        this.fecha = LocalDateTime.now();
        this.hora = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public LocalDateTime getHora() { return hora; }
    public void setHora(LocalDateTime hora) { this.hora = hora; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getImagenProducto() { return imagenProducto; }
    public void setImagenProducto(String imagenProducto) { this.imagenProducto = imagenProducto; }
}
