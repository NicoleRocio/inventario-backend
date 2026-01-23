package com.plataforma.plataforma.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoSolicitud;
    private String motivo;
    private Boolean esPorHoras;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    // Campos PRO nuevos
    private Integer totalDias;
    private Double totalHoras;
    private String archivoEvidencia;   // <--- Nuevo
    private String comentarioRechazo;  // <--- Nuevo
    private LocalDateTime fechaSolicitud = LocalDateTime.now(); // <--- Auditoría automática

    private String descripcion;
    private String estado = "Pendiente";

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Solicitud() {}

    // ================= GETTERS Y SETTERS =================
    // (Te incluyo los nuevos, asegúrate de tener los de fecha/hora anteriores también)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipoSolicitud() { return tipoSolicitud; }
    public void setTipoSolicitud(String tipoSolicitud) { this.tipoSolicitud = tipoSolicitud; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public Boolean getEsPorHoras() { return esPorHoras; }
    public void setEsPorHoras(Boolean esPorHoras) { this.esPorHoras = esPorHoras; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    // 👇 LOS NUEVOS 👇
    public Integer getTotalDias() { return totalDias; }
    public void setTotalDias(Integer totalDias) { this.totalDias = totalDias; }

    public Double getTotalHoras() { return totalHoras; }
    public void setTotalHoras(Double totalHoras) { this.totalHoras = totalHoras; }

    public String getArchivoEvidencia() { return archivoEvidencia; }
    public void setArchivoEvidencia(String archivoEvidencia) { this.archivoEvidencia = archivoEvidencia; }

    public String getComentarioRechazo() { return comentarioRechazo; }
    public void setComentarioRechazo(String comentarioRechazo) { this.comentarioRechazo = comentarioRechazo; }

    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
}