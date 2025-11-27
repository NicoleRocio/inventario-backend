package com.plataforma.plataforma.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // Aquí SÍ enviamos los roles al frontend
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "empleado_roles",
            joinColumns = @JoinColumn(name = "empleado_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    @JsonManagedReference
    private Set<Rol> roles;

    // Usuario → relación 1:1 (no queremos recursión)
    @OneToOne(mappedBy = "empleado", cascade = CascadeType.ALL)
    @JsonIgnore
    private Usuario usuario;

    // Getters y Setters...

    public Long getId()
    { return id; }

    public void setId(Long id)
    { this.id = id; }

    public String getNombre()
    { return nombre; }

    public void setNombre(String nombre)
    { this.nombre = nombre; }

    public Set<Rol> getRoles()
    { return roles; }

    public void setRoles(Set<Rol> roles)
    { this.roles = roles; }

    public Usuario getUsuario()
    { return usuario; }

    public void setUsuario(Usuario usuario)
    { this.usuario = usuario; }

}
