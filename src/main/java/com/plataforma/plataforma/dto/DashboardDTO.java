package com.plataforma.plataforma.dto;

public class DashboardDTO {
    private long totalActivos;
    private long totalIncidencias;
    private long totalPedidos;

    public DashboardDTO(long totalActivos, long totalIncidencias, long totalPedidos) {
        this.totalActivos = totalActivos;
        this.totalIncidencias = totalIncidencias;
        this.totalPedidos = totalPedidos;
    }

    // Getters y Setters necesarios para que se convierta a JSON
    public long getTotalActivos() { return totalActivos; }
    public void setTotalActivos(long totalActivos) { this.totalActivos = totalActivos; }

    public long getTotalIncidencias() { return totalIncidencias; }
    public void setTotalIncidencias(long totalIncidencias) { this.totalIncidencias = totalIncidencias; }

    public long getTotalPedidos() { return totalPedidos; }
    public void setTotalPedidos(long totalPedidos) { this.totalPedidos = totalPedidos; }
}
