package com.GeoMarket.Reporte_Service.controller;

import java.time.LocalDate;
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

import com.GeoMarket.Reporte_Service.model.Reporte;
import com.GeoMarket.Reporte_Service.service.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {



    @Autowired
    private ReporteService reporteService;

  


    // CREAR REPORTE
    @PostMapping("/crear")
    public Reporte crear(@RequestParam Long idUsuario, @RequestParam String nombre, @RequestParam(required = false) String descripcion, @RequestParam Long idTipo) {

        return reporteService.crearReporte(idUsuario, nombre, descripcion, idTipo);
    }



    // OBTENER REPORTES DE UN USUARIO
    @GetMapping("/usuario/{idUsuario}")
    public List<Reporte> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return reporteService.obtenerPorUsuario(idUsuario);
    }



   


    // LISTAR TODOS LOS REPORTES
    @GetMapping
    public List<Reporte> listarTodos() {
        return reporteService.listarTodos();
    }

    // OBTENER REPORTE POR ID
    @GetMapping("/{id}")
    public Reporte obtenerPorId(@PathVariable Long id) {
        return reporteService.obtenerPorId(id);
    }

    // CAMBIAR ESTADO
    @PutMapping("/estado/{id}")
    public Reporte cambiarEstado(@PathVariable Long id,@RequestParam String estado) {
        return reporteService.cambiarEstado(id, estado);
    }

    // CAMBIAR PRIORIDAD
    @PutMapping("/prioridad/{id}")
    public Reporte cambiarPrioridad( @PathVariable Long id, @RequestParam String prioridad) {
        return reporteService.cambiarPrioridad(id, prioridad);
    }



    // ELIMINAR REPORTE
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        reporteService.eliminar(id);
        return "Reporte eliminado correctamente";
    }


    


    // BUSCAR POR ESTADO
    @GetMapping("/estado")
    public List<Reporte> buscarPorEstado(@RequestParam String estado) {
        return reporteService.buscarPorEstado(estado);
    }

    // BUSCAR POR TIPO
    @GetMapping("/tipo/{idTipo}")
    public List<Reporte> buscarPorTipo(@PathVariable Long idTipo) {
        return reporteService.buscarPorTipo(idTipo);
    }

    // BUSCAR POR FECHA
    @GetMapping("/fecha")
    public List<Reporte> buscarPorFecha(@RequestParam String fecha) {
        LocalDate fechaParseada = LocalDate.parse(fecha);
        return reporteService.buscarPorFecha(fechaParseada);
    }




   
    // CONTAR POR ESTADO
    @GetMapping("/conteo/estado")
    public Long contarPorEstado(@RequestParam String estado) {
        return reporteService.contarPorEstado(estado);
    }


    // CONTAR POR USUARIO
    @GetMapping("/conteo/usuario/{idUsuario}")
    public Long contarPorUsuario(@PathVariable Long idUsuario) {
        return reporteService.contarPorUsuario(idUsuario);
    }



    // RESUMEN GENERAL
    @GetMapping("/resumen")
    public String resumen() {
        return reporteService.resumenGeneral();
        
    }
}