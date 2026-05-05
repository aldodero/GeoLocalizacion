package com.GeoMarket.Notificacion_Service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.GeoMarket.Notificacion_Service.model.TipoNotificacion;
import com.GeoMarket.Notificacion_Service.service.TipoNotificacionService;

@RestController
@RequestMapping("/api/tipoNotificaciones")
public class TipoNotificacionController {


     @Autowired
    private TipoNotificacionService tiposervice;

  
    // CREAR
    @PostMapping("/crear")
    public TipoNotificacion crear(@RequestBody TipoNotificacion tipo) {
        return tiposervice.crearTipo(tipo);
    }


    // LISTAR TODOS
    @GetMapping("/listado")
    public List<TipoNotificacion> listar() {
        return tiposervice.listar();
    }


    // OBTENER POR ID
    @GetMapping("/Obtener/{id}")
    public TipoNotificacion obtener(@PathVariable Long id) {
        return tiposervice.obtenerPorId(id);
    }


    // ACTUALIZAR
    @PutMapping("/actualizar/{id}")
    public TipoNotificacion actualizar(@PathVariable Long id,
                                       @RequestBody TipoNotificacion datos) {
        return tiposervice.actualizar(id, datos);
    }



    // ELIMINAR
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        tiposervice.eliminar(id);
        return "Tipo de notificación eliminado";
    }



    // BUSCAR POR NOMBRE
    @GetMapping("/buscar")
    public List<TipoNotificacion> buscar(@RequestParam String nombre) {
        return tiposervice.buscarPorNombre(nombre);
    }


    
    
}
