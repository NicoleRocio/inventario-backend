package com.plataforma.plataforma.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List; // <--- 1. ¡IMPORTANTE! Agrega esta importación

@Entity
@Table(name = "usuarios")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String nombre;

    @OneToOne
    @JoinColumn(name = "empleado_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"usuario"})
    private Empleado empleado;

    // =================================================================
    // 🔥 ESTA ES LA PARTE QUE FALTABA: CONEXIÓN CON ROLES
    // =================================================================
    // "EAGER" obliga a Spring a traer los roles inmediatamente
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_rol",            // <--- Coincide con la tabla SQL que creaste
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles;
    // =================================================================

    // Constructor vacío (OBLIGATORIO)
    public Usuario() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }

    // 🔥 Getter y Setter de Roles (IMPRESCINDIBLE)
    public List<Rol> getRoles() { return roles; }
    public void setRoles(List<Rol> roles) { this.roles = roles; }
}