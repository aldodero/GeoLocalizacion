package com.GeoMarket.Reporte_Service.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GeoMarket.Reporte_Service.model.Bitacora;
import com.GeoMarket.Reporte_Service.service.BitacoraService;

@RestController
@RequestMapping("/api/bitacora")
public class BitacoraController {


    @Autowired
    private BitacoraService bitacoraService;

    // ==============================
    // 🧑‍💼 ADMIN (CONSULTAS)
    // ==============================

    // OBTENER TODA LA BITÁCORA
    @GetMapping
    public List<Bitacora> obtenerTodo() {
        return bitacoraService.obtenerTodo();
    }




    // OBTENER POR USUARIO
    @GetMapping("/usuario/{idUsuario}")
    public List<Bitacora> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return bitacoraService.obtenerPorUsuario(idUsuario);
    }

    // OBTENER POR TABLA
    @GetMapping("/obtener-tabla")
    public List<Bitacora> obtenerPorTabla(@RequestParam String tabla) {
        return bitacoraService.obtenerPorTabla(tabla);
    }

    // OBTENER POR ACCIÓN
    @GetMapping("/obtener-accion")
    public List<Bitacora> obtenerPorAccion(@RequestParam String accion) {
        return bitacoraService.obtenerPorAccion(accion);
    }

    // OBTENER POR FECHA
    @GetMapping("/obtener-fecha")
    public List<Bitacora> obtenerPorFecha(@RequestParam String fecha) {
        LocalDate fechaParseada = LocalDate.parse(fecha);
        return bitacoraService.obtenerPorFecha(fechaParseada);
    }

   
    // REGISTRAR ACCIÓN MANUAL
    @PostMapping("/registrar")
    public Bitacora registrar(@RequestParam String accion, @RequestParam String tabla,@RequestParam Long idUsuario) {
        return bitacoraService.registrarAccion(accion, tabla, idUsuario);
    }



    // MÉTODOS RÁPIDOS
    @PostMapping("/crear")
    public String registrarCreacion(@RequestParam String tabla, @RequestParam Long idUsuario) {
        bitacoraService.registrarCreacion(tabla, idUsuario);
        return "Registro de creación guardado";
    }



    @PostMapping("/actualizar")
    public String registrarActualizacion(@RequestParam String tabla, @RequestParam Long idUsuario) {
        bitacoraService.registrarActualizacion(tabla, idUsuario);
        return "Registro de actualización guardado";
    }



    @PostMapping("/eliminar")
    public String registrarEliminacion(@RequestParam String tabla, @RequestParam Long idUsuario) {
        bitacoraService.registrarEliminacion(tabla, idUsuario);
        return "Registro de eliminación guardado";
    }

    @PostMapping("/login")
    public String registrarLogin(@RequestParam Long idUsuario) {
        bitacoraService.registrarLogin(idUsuario);
        return "Login registrado";
    }

    @PostMapping("/sospechoso")
    public String registrarActividad(@RequestParam Long idUsuario) {
        bitacoraService.registrarActividadInusual(idUsuario);
        return "Actividad inusual registrada";
    }


    
    @GetMapping("/resumen/{idUsuario}")
    public String resumenUsuario(@PathVariable Long idUsuario) {
        return bitacoraService.resumenUsuario(idUsuario);
    }

  

    
}