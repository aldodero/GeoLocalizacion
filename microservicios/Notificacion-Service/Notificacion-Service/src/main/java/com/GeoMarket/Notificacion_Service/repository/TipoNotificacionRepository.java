package com.GeoMarket.Notificacion_Service.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GeoMarket.Notificacion_Service.model.TipoNotificacion;




@Repository
public interface  TipoNotificacionRepository extends JpaRepository<TipoNotificacion, Long> {

    //BUSCAR NOMBRE DEL TIPO DE NOTIFICACION
    List<TipoNotificacion> findByNombreNotificacionContainingIgnoreCase(String nombre);

    Optional<TipoNotificacion> findByNombreNotificacion(String nombre);
}

