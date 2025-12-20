package com.plataforma.plataforma.dto;

import java.util.List;

public class UsuarioRegistroDTO {
    // Estos son los campos exactos que enviaremos desde el formulario de React
    private String username;
    private String password;
    private String nombre;
    private List<Long> rolesIds; // Recibiremos una lista de IDs (ej: [2] para Administrativo)

    // Constructor vacío (necesario para Spring)
    public UsuarioRegistroDTO() {}

    // Constructor con datos
    public UsuarioRegistroDTO(String username, String password, String nombre, List<Long> rolesIds) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.rolesIds = rolesIds;
    }

    // Getters y Setters comando de restriccion para borrar es
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Long> getRolesIds() { return rolesIds; }
    public void setRolesIds(List<Long> rolesIds) { this.rolesIds = rolesIds; }
}