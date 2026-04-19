package com.GeoMarket.Reporte_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GeoMarket.Reporte_Service.model.TipoReporte;
import com.GeoMarket.Reporte_Service.repository.TipoReporteRepository;

@Service
public class TipoReporteService {

    @Autowired
    private TipoReporteRepository tipoReporterepository;



    
    //validaciones//
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
    }





    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre inválido");
        }
    }



    // LISTAR SOLO TIPOS DE REPORTES ACTIVOS
    public List<TipoReporte> listarActivos() {
        return tipoReporterepository.findByEstadoIgnoreCase("ACTIVO");
    }



    // OBTENER TIPO REPORTE POR ID
    public TipoReporte obtenerPorId(Long id) {
        validarId(id);
        return tipoReporterepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de reporte no encontrado"));
    }



    // CREAR TIPO
    public TipoReporte crear(String nombre) {

        validarNombre(nombre);

        if (tipoReporterepository.existsByNombreTipoReporteIgnoreCase(nombre)) {
            throw new RuntimeException("El tipo ya existe");
        }

        TipoReporte t = new TipoReporte();
        t.setNombreTipoReporte(nombre.toUpperCase());
        t.setEstado("ACTIVO");

        return tipoReporterepository.save(t);
    }

    // LISTAR TODOS LOS TIPOS 
    public List<TipoReporte> listarTodos() {
        return tipoReporterepository.findAll();
    }



    // ACTUALIZAR NOMBRE DEL TIPO REPORTE
    public TipoReporte actualizar(Long id, String nuevoNombre) {

        validarId(id);
        validarNombre(nuevoNombre);

        TipoReporte t = obtenerPorId(id);

        if (tipoReporterepository.existsByNombreTipoReporteIgnoreCase(nuevoNombre)) {
            throw new RuntimeException("Ya existe ese nombre");
        }

        t.setNombreTipoReporte(nuevoNombre.toUpperCase());

        return tipoReporterepository.save(t);
    }



    // DESACTIVAR TIPO DE REPORTE
    public TipoReporte desactivar(Long id) {

        validarId(id);

        TipoReporte t = obtenerPorId(id);
        t.setEstado("INACTIVO");

        return tipoReporterepository.save(t);
    }



    // ACTIVAR
    public TipoReporte activar(Long id) {
        validarId(id);
        
        TipoReporte t = obtenerPorId(id);
        t.setEstado("ACTIVO");

        return tipoReporterepository.save(t);
    }



    // ELIMINAR 
    public void eliminar(Long id) {

        validarId(id);

        if (!tipoReporterepository.existsById(id)) {
            throw new RuntimeException("Tipo no existe");
        }

        tipoReporterepository.deleteById(id);
    }



    //FILTRO BUSCAR TIPO DE REPORTE POR NOMBRE
    public List<TipoReporte> buscarPorNombre(String nombre) {
        validarNombre(nombre);
        return tipoReporterepository.findByNombreTipoReporteContainingIgnoreCase(nombre);
    }

    public List<TipoReporte> buscarPorEstado(String estado) {
        validarNombre(estado);
        return tipoReporterepository.findByEstadoIgnoreCase(estado);
    }

   

    //CONTADOR TIPO REPORTES ACTIVOS
    public Long contarActivos() {
        return tipoReporterepository.countByEstadoIgnoreCase("ACTIVO");
    }
    

    //CONTADOR TIPOREPORTES INACTIVOS
    public Long contarInactivos() {
        return tipoReporterepository.countByEstadoIgnoreCase("INACTIVO");
    }


    //RESUMEN TIPOS
    public String resumen() {
        Long activos = contarActivos();
        Long inactivos = contarInactivos();

        long totalreportes= activos + inactivos;

        return "Tipos → Activos: " + activos + 
        " | Inactivos: " + inactivos + 

        " | total de reportes: " + totalreportes;
    }







    
}