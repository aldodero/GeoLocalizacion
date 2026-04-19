package com.GeoMarket.Notificacion_Service.controller;

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

import com.GeoMarket.Notificacion_Service.model.Notificacion;
import com.GeoMarket.Notificacion_Service.service.NotificacionService;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {


    @Autowired
    private NotificacionService notificacionService;

    


    //TRABAJADOR//
    //OBTENER TODAS LAS NOTIFICACIONES DE UN USUARIO
    @GetMapping("/usuario/{idUsuario}")
    public List<Notificacion> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return notificacionService.obtenerPorUsuario(idUsuario);
    }

    //OBTENER NOTIFICACIONES SOLO NO LEÍDAS
    @GetMapping("/usuario/{idUsuario}/no-leidas")
    public List<Notificacion> obtenerNoLeidas(@PathVariable Long idUsuario) {
        return notificacionService.obtenerNoLeidas(idUsuario);
    }

    // MARCAR COMO LEÍDA
    @PutMapping("/leer/{idNotificacion}")
    public Notificacion marcarLeida(@PathVariable Long idNotificacion) {
        return notificacionService.marcarComoLeida(idNotificacion);
    }

    // ELIMINAR NOTIFICACIÓN POR ID 
    @DeleteMapping("/Eliminar/{idNotificacion}")
    public String eliminar(@PathVariable Long idNotificacion) {
        notificacionService.eliminar(idNotificacion);
        return "Notificación eliminada";
    }

 
    //ADMIN///
    // CREAR NOTIFICACIÓN GENERAL
    @PostMapping("/crear")
    public Notificacion crear(
            @RequestParam Long idUsuario,
            @RequestParam String mensaje,
            @RequestParam Long idTipo) {

        return notificacionService.crearNotificacionPorNombre(idUsuario, mensaje, mensaje);
    }

    
    
    //  NOTIFICACION DE FAVORITO
    @PostMapping("/evento/favorito")
    public String notificarFavorito(@RequestParam Long idUsuario) {
        notificacionService.notificarFavorito(idUsuario);
        return "Notificación de favorito enviada";
    }

    // NOTIFICACION DE ESCANEO FRECUENTE 
    @PostMapping("/evento/escaneo")
    public String notificarEscaneo(@RequestParam Long idUsuario) {
        notificacionService.notificarEscaneoFrecuente(idUsuario);
        return "Notificación de escaneo enviada";
    }

  
    //ADMIN
    // CREAR ALERTA ADMIN
    @PostMapping("/admin")
    public String notificarAdmin(@RequestParam String mensaje) {
        notificacionService.notificarAdmin(mensaje);
        return "Notificación admin enviada";
    }
}
