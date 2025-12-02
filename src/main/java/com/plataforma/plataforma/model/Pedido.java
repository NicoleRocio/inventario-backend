package com.plataforma.plataforma.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //  AHORA el pedido pertenece a un usuario (id real)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"empleado", "password"})
    private Usuario usuario;

    private LocalDateTime fecha;

    private String estado = "En espera";

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DetallePedido> detalles;

    public Pedido() {
        this.fecha = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<DetallePedido> getDetalles() { return detalles; }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
        if (detalles != null) {
            detalles.forEach(d -> d.setPedido(this));
        }
    }
}
