package com.GeoMarket.Reporte_Service.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GeoMarket.Reporte_Service.model.Reporte;
import com.GeoMarket.Reporte_Service.model.TipoReporte;
import com.GeoMarket.Reporte_Service.repository.ReporteRepository;
import com.GeoMarket.Reporte_Service.repository.TipoReporteRepository;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporterepository;

    @Autowired
    private TipoReporteRepository tiporeporterepository;



    //VALIDACIONES//
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
    }
    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(campo + " inválido");
        }
    }


    //TRABAJADOR//
    // CREAR REPORTE
    public Reporte crearReporte(Long idUsuario, String nombre, String descripcion, Long idTipo) {

        validarId(idUsuario);
        validarTexto(nombre, "Nombre");

        TipoReporte tipo = tiporeporterepository.findById(idTipo)
                .orElseThrow(() -> new RuntimeException("Tipo de reporte no encontrado"));

        Reporte r = new Reporte();
        r.setIdUsuario(idUsuario);
        r.setNombreReporte(nombre);
        r.setDescripcion(descripcion);
        r.setFechaReporte(LocalDate.now());
        r.setHoraReporte(LocalDateTime.now());
        r.setEstadoReporte("PENDIENTE");
        r.setPrioridad("MEDIA");
        r.setTipoReporte(tipo);

        return reporterepository.save(r);
    }



    // OBTENER REPORTES DE USUARIO POR ID
    public List<Reporte> obtenerPorUsuario(Long idUsuario) {
        validarId(idUsuario);
        return reporterepository.findByIdUsuario(idUsuario);
    }





    // ACCCIONES DE  ADMIN//
    // LISTAR TODOS TODOS LOS REPORTES
    public List<Reporte> listarTodos() {
        return reporterepository.findAll();
    }

    // OBTENER UN REPORTE POR ID
    public Reporte obtenerPorId(Long idReporte) {
        validarId(idReporte);
        return reporterepository.findById(idReporte)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }


    // CAMBIAR ESTADO POR ID REPORTE
    public Reporte cambiarEstado(Long idReporte, String nuevoEstado) {

        validarId(idReporte);
        validarTexto(nuevoEstado, "Estado");

        Reporte r = obtenerPorId(idReporte);
        r.setEstadoReporte(nuevoEstado.toUpperCase());

        return reporterepository.save(r);
    }


    // CAMBIAR PRIORIDAD DE REPORTE POR ID REPORTE
    public Reporte cambiarPrioridad(Long idReporte, String prioridad) {
        validarId(idReporte);
        validarTexto(prioridad, "Prioridad");

        Reporte r = obtenerPorId(idReporte);
        r.setPrioridad(prioridad.toUpperCase());

        return reporterepository.save(r);
    }


    // ELIMINAR REPORTE POR ID REPORTE
    public void eliminar(Long idReporte) {
        validarId(idReporte);

        if (!reporterepository.existsById(idReporte)) {
            throw new RuntimeException("Reporte no existe");
        }

        reporterepository.deleteById(idReporte);
    }




    //FILTROS//
    //BUSCAR REPORTE POR ESTADO
    public List<Reporte> buscarPorEstado(String estado) {
        validarTexto(estado, "Estado");
        return reporterepository.findByEstadoReporteIgnoreCase(estado);
    }

    //BUSCAR REPORTE POR TIPO REPORTE
    public List<Reporte> buscarPorTipo(Long idTipo) {
        validarId(idTipo);
        return reporterepository.findByTipoReporte_IdTipoReporte(idTipo);
    }

    //BUSCAR REPORTE POR FECHA
    public List<Reporte> buscarPorFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("Fecha inválida");
        }
        return reporterepository.findByFechaReporte(fecha);
    }

    

    //CONTADOR DE REPORETE POR ESTADO
    public Long contarPorEstado(String estado) {
        return reporterepository.countByEstadoReporteIgnoreCase(estado);
    }

    //CONTADOR DE USUARIO
    public Long contarPorUsuario(Long idUsuario) {
        validarId(idUsuario);
        return reporterepository.countByIdUsuario(idUsuario);
    }


    
    //RESUMEN GENERAL 
    public String resumenGeneral() {

        Long pendientes = contarPorEstado("PENDIENTE");
        Long resueltos = contarPorEstado("RESUELTO");
        
        long totalReportes = pendientes + resueltos;


        return "Reportes → Pendientes: " + pendientes + 
        " | Resueltos: " + resueltos +
        " | Reportes totales : " + totalReportes;
    }
}