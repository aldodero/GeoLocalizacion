package com.GeoMarket.Reporte_Service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GeoMarket.Reporte_Service.model.TipoReporte;
import com.GeoMarket.Reporte_Service.service.TipoReporteService;

@RestController
@RequestMapping("/api/tipo-reportes")
public class TipoReporteController {

    @Autowired
    private TipoReporteService tipoReporteService;

   
    // LISTAR SOLO TIPOS ACTIVOS
    @GetMapping("/listar-activos")
    public List<TipoReporte> listarActivos() {
        return tipoReporteService.listarActivos();
    }


    // OBTENER POR ID
    @GetMapping("/Obtener-Tipo/{id}")
    public TipoReporte obtenerPorId(@PathVariable Long id) {
        return tipoReporteService.obtenerPorId(id);
    }

    // CREAR TIPO
    @PostMapping("/crear")
    public TipoReporte crear(@RequestParam String nombre) {
        return tipoReporteService.crear(nombre);
    }



    // LISTAR TODOS
    @GetMapping("/listar")
    public List<TipoReporte> listarTodos() {
        return tipoReporteService.listarTodos();
    }



    // ACTUALIZAR NOMBRE
    @PutMapping("/actualizar/{id}")
    public TipoReporte actualizar(
            @PathVariable Long id,
            @RequestParam String nombre) {

        return tipoReporteService.actualizar(id, nombre);
    }



    // DESACTIVAR
    @PutMapping("/desactivar/{id}")
    public TipoReporte desactivar(@PathVariable Long id) {
        return tipoReporteService.desactivar(id);
    }



    // ACTIVAR
    @PutMapping("/activar/{id}")
    public TipoReporte activar(@PathVariable Long id) {
        return tipoReporteService.activar(id);
    }



    // ELIMINAR
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        tipoReporteService.eliminar(id);
        return "Tipo de reporte eliminado";
    }


   

    // BUSCAR POR NOMBRE
    @GetMapping("/buscar")
    public List<TipoReporte> buscarPorNombre(@RequestParam String nombre) {
        return tipoReporteService.buscarPorNombre(nombre);
    }
    


    // BUSCAR POR ESTADO
    @GetMapping("/buscar-estado")
    public List<TipoReporte> buscarPorEstado(@RequestParam String estado) {
        return tipoReporteService.buscarPorEstado(estado);
    }


  
    // CONTAR ACTIVOS
    @GetMapping("/conteo/activos")
    public Long contarActivos() {
        return tipoReporteService.contarActivos();
    }



    // CONTAR INACTIVOS
    @GetMapping("/conteo/inactivos")
    public Long contarInactivos() {
        return tipoReporteService.contarInactivos();
    }



    // RESUMEN
    @GetMapping("/resumen-tipo-Reporte")
    public String resumen() {
        return tipoReporteService.resumen();
    }
}