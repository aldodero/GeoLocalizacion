package com.GeoMarket.Notificacion_Service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearNotificacionRequest {

    private Long idUsuario;
    private Long idLocal;
    private String titulo;
    private String descripcion;
    private Long idTipo;

}