package com.plataforma.plataforma.service;

import com.plataforma.plataforma.dto.IncidenciaRequestDTO;
import com.plataforma.plataforma.model.Incidencia;
import com.plataforma.plataforma.model.Usuario;
import com.plataforma.plataforma.repository.IncidenciaRepository;
import com.plataforma.plataforma.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;

    public IncidenciaService(IncidenciaRepository incidenciaRepository,
                             UsuarioRepository usuarioRepository) {
        this.incidenciaRepository = incidenciaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Incidencia registrarIncidencia(IncidenciaRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Incidencia incidencia = new Incidencia();
        incidencia.setUsuario(usuario);
        incidencia.setArea(dto.getArea());
        incidencia.setDescripcion(dto.getDescripcion());
        incidencia.setProductoId(dto.getProductoId());
        incidencia.setNombreProducto(dto.getNombreProducto());
        incidencia.setImagenProducto(dto.getImagenProducto());

        return incidenciaRepository.save(incidencia);
    }

    public List<Incidencia> obtenerPorUsuario(Long usuarioId) {
        return incidenciaRepository.findByUsuarioId(usuarioId);
    }

    public List<Incidencia> listarTodas() {
        return incidenciaRepository.findAll();
    }

    public Incidencia actualizarEstado(Long id, String estado) {
        Incidencia incidencia = incidenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        incidencia.setEstado(estado);
        return incidenciaRepository.save(incidencia);
    }
    public void eliminar(Long id) {
        incidenciaRepository.deleteById(id);
    }

}
