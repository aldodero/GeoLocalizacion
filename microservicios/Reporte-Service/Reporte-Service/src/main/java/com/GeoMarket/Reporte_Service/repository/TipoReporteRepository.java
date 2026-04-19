package com.GeoMarket.Reporte_Service.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GeoMarket.Reporte_Service.model.TipoReporte;

@Repository
public interface TipoReporteRepository extends  JpaRepository <TipoReporte, Long> {

    List<TipoReporte> findByEstadoIgnoreCase(String estado);

    boolean existsByNombreTipoReporteIgnoreCase(String nombreTipoReporte);

    List<TipoReporte> findByNombreTipoReporteContainingIgnoreCase(String nombreTipoReporte);

    Long countByEstadoIgnoreCase(String estado);
}



