package com.GeoMarket.Notificacion_Service.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



    // CREAR NOTIFICACION en general 
public Notificacion crearNotificacionPorNombre(Long idUsuario,Long idLocal,String titulo,String descripcion,Long idTipo) {

    TipoNotificacion tipo = tiponotificacionrepository.findById(idTipo)
            .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

    Notificacion n = new Notificacion();
    n.setIdUsuario(idUsuario);
    n.setIdLocal(idLocal);
    n.setTitulo(titulo);
    n.setDescripcion(descripcion);

    // opcional para compatibilidad
    n.setMensaje(titulo);

    n.setFechaNotificacion(LocalDate.now());
    n.setHoraNotificacion(LocalDateTime.now());
    n.setEstadoNotificacion("NO_LEIDA");
    n.setTipoNotificacion(tipo);

    return notificacionrepository.save(n);
}




    //OBTENER NOTIFICACION POR USUARIO CON ID
    public List<Notificacion> obtenerPorUsuario(Long idUsuario,Long idLocal) {

        if (idUsuario == null) {
            throw new IllegalArgumentException("Usuario inválido");
        }
        if(idLocal == null){
            throw  new IllegalArgumentException("local invalido");
        }
        return notificacionrepository.findByIdUsuarioAndIdLocalOrderByFechaNotificacionDesc(idUsuario, idLocal);
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





    
    // NOTIFICACION FAVORITO automatica
    public void notificarFavorito(Long idUsuario, Long idLocal) {

        crearNotificacionPorNombre(
                idUsuario,
                idLocal,
                "Producto favorito",
                "Agregaste un producto a favoritos ",
                1L
        );
    }

    // ESCANEO FRECUENTE automatico
    public void notificarEscaneoFrecuente(Long idUsuario, Long idLocal) {

        crearNotificacionPorNombre(
                idUsuario,
                idLocal,
                "Escaneo frecuente",
                "Has escaneado este producto muchas veces",
                2L
        );
    }

    // ADMIN
    public void notificarAdmin(Long idUsuario,Long idLocal,String mensaje) {

        crearNotificacionPorNombre(
                idUsuario,
                idLocal,
                "Aviso del administrador",
                mensaje,
                3L
        );
    }

    //contar las notificaciones no leidas
    public Long contarNoLeidas(Long idUsuario,Long idLocal){
        return notificacionrepository.countByIdUsuarioAndIdLocalAndEstadoNotificacion(idUsuario, idLocal, "NO_LEIDA");
    }


}




