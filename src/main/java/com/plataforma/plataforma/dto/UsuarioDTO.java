package com.plataforma.plataforma.dto;

import java.util.List;

public class UsuarioDTO {
    private Long id;
    private String username;
    private String nombre;
    private List<String> roles; // <--- Aquí enviaremos solo los nombres ("DOCENTE", "ADMIN")

    // Constructor
    public UsuarioDTO(Long id, String username, String nombre, List<String> roles) {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.roles = roles;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}