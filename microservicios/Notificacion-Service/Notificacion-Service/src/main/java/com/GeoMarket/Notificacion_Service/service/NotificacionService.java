package com.GeoMarket.Notificacion_Service.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.GeoMarket.Notificacion_Service.model.Notificacion;
import com.GeoMarket.Notificacion_Service.model.TipoNotificacion;
import com.GeoMarket.Notificacion_Service.repository.NotificacionRepository;
import com.GeoMarket.Notificacion_Service.repository.TipoNotificacionRepository;


@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionrepository;

    @Autowired
    private TipoNotificacionRepository tiponotificacionrepository;







    //CREAR NOTIFICACION//
    public Notificacion crearNotificacionPorNombre(Long idUsuario, String mensaje, String nombreTipo) {

    TipoNotificacion tipo = tiponotificacionrepository.findByNombreNotificacion(nombreTipo)
        .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

    Notificacion n = new Notificacion();
    n.setIdUsuario(idUsuario);
    n.setMensaje(mensaje);
    n.setFechaNotificacion(LocalDate.now());
    n.setHoraNotificacion(LocalDateTime.now());
    n.setEstadoNotificacion("NO_LEIDA");
    n.setTipoNotificacion(tipo);

    return notificacionrepository.save(n);
}




    //OBTENER NOTIFICACION POR USUARIO CON ID
    public List<Notificacion> obtenerPorUsuario(Long idUsuario) {

        if (idUsuario == null) {
            throw new IllegalArgumentException("Usuario inválido");
        }
        return notificacionrepository.findByIdUsuarioOrderByFechaNotificacionDesc(idUsuario);
    }





    //OBTENER NOTIFICACION LEIDAS POR ID DE USUARIO
    public List<Notificacion> obtenerNoLeidas(Long idUsuario) {
        return notificacionrepository.findByIdUsuarioAndEstadoNotificacion(idUsuario, "NO_LEIDA");
    }

    ///MARCAR NOTIFICACIONES LEIDAS
    public Notificacion marcarComoLeida(Long idNotificacion) {

        Notificacion n = notificacionrepository.findById(idNotificacion)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        n.setEstadoNotificacion("LEIDA");

        return notificacionrepository.save(n);
    }

    


    //ELIMINAR NOTIFICACION
    public void eliminar(Long idNotificacion) {

        if (!notificacionrepository.existsById(idNotificacion)) {
            throw new RuntimeException("No existe la notificación");
        }

        notificacionrepository.deleteById(idNotificacion);
    }





    
    //NOTIFICACION AUTOMATICAS//
    public void notificarFavorito(Long idUsuario) {
    crearNotificacionPorNombre(idUsuario, "Agregaste un producto ⭐", "FAVORITO");
}

    //ESCANEO FRECUENTE
    public void notificarEscaneoFrecuente(Long idUsuario) {
    crearNotificacionPorNombre(
        idUsuario,
        "Has escaneado este producto muchas veces",
        "ESCANEO");
    }

 
    public void notificarAdmin(String mensaje) {
    crearNotificacionPorNombre(
        1L, 
        mensaje,
        "ADMIN"
    );
}






}




