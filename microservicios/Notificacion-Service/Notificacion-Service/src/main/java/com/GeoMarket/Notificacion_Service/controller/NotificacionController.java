package com.GeoMarket.Notificacion_Service.controller;

import java.util.List;

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

import com.GeoMarket.Notificacion_Service.dto.CrearNotificacionRequest;
import com.GeoMarket.Notificacion_Service.model.Notificacion;
import com.GeoMarket.Notificacion_Service.service.NotificacionService;



@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {


    @Autowired
    private NotificacionService notificacionService;

    


    //TRABAJADOR//

    //OBTENER NOTIFICACIONES POR USUARIO Y LOCAL
    @GetMapping("/usuario-local/{idUsuario}/{idLocal}")
    public List<Notificacion> obtenerPorUsuario(@PathVariable Long idUsuario,@PathVariable Long idLocal) {
        return notificacionService.obtenerPorUsuario(idUsuario,idLocal);
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

 
  
    // CREAR NOTIFICACIÓN GENERAL
        @PostMapping("/crear")
        public Notificacion crear(@RequestBody CrearNotificacionRequest request){
    return notificacionService.crearNotificacionPorNombre(
        request.getIdUsuario(),
        request.getIdLocal(),
        request.getTitulo(),
        request.getDescripcion(),
        request.getIdTipo()
    );
}


    
    
    //  NOTIFICACION DE FAVORITO
    @PostMapping("/evento/favorito")
    public String notificarFavorito(@RequestParam Long idUsuario,Long idLocal) {
        notificacionService.notificarFavorito(idUsuario,idLocal);
        return "Notificación de favorito enviada";
    }

    // NOTIFICACION DE ESCANEO FRECUENTE 
    @PostMapping("/evento/escaneo")
    public String notificarEscaneo(@RequestParam Long idUsuario, Long idLocal) {
        notificacionService.notificarEscaneoFrecuente(idUsuario, idLocal);
        return "Notificación de escaneo enviada";
    }

    //ADMIN
    // CREAR ALERTA ADMIN
    @PostMapping("/admin")
    public String notificarAdmin(@RequestParam Long idUsuario,Long idLocal,String mensaje) {
        notificacionService.notificarAdmin(idUsuario,idLocal,mensaje);
        return "Notificación admin enviada";
    }

    
    //NOTIFICACIONES NO LEIDAS
    @GetMapping("usuario-local/{idUsuario}/{idLocal}/conteo")
    public Long contarNoLeidas(@PathVariable Long idUsuario ,@PathVariable Long idLocal){
        return notificacionService.contarNoLeidas(idUsuario,idLocal);
    }

    

}
