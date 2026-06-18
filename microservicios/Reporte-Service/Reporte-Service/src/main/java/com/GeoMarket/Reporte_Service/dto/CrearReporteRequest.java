package com.GeoMarket.Reporte_Service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearReporteRequest {

    private Long idUsuario;
    private long idLocal;
    private String nombreReporte;
    private String descripcion;
    private String prioridad;
    private Long idTipo;
}