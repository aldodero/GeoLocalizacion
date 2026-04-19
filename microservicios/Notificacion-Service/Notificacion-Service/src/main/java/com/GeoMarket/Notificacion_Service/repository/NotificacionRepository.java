package com.GeoMarket.Notificacion_Service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.GeoMarket.Notificacion_Service.model.Notificacion;





@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long>  {

    //BUSCAR NOTIFICACION POR ID USUARIO POR FECHA
    List<Notificacion> findByIdUsuarioOrderByFechaNotificacionDesc(Long idUsuario);

    //BUSCAR//VER EL ESTADO DE LA NOTIFICACION POR ID DE USUARIO
    List<Notificacion> findByIdUsuarioAndEstadoNotificacion(Long idUsuario, String estado);

}
    
