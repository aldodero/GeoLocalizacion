package com.GeoMarket.Reporte_Service.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GeoMarket.Reporte_Service.model.Reporte;




@Repository
public interface ReporteRepository extends  JpaRepository<Reporte, Long> {

    //buscar usuario por id
     List<Reporte> findByIdUsuario(Long idUsuario);
    //busar  por estado de reporte
    List<Reporte> findByEstadoReporteIgnoreCase(String estadoReporte);
    //bscar tipo de reporete por id tipo
    List<Reporte> findByTipoReporte_IdTipoReporte(Long idTipoReporte);
    //buscar por fecha de reporte
    List<Reporte> findByFechaReporte(LocalDate fechaReporte);
    //contador de reportes
    Long countByEstadoReporteIgnoreCase(String estadoReporte);
    //contador de usuario
    Long countByIdUsuario(Long idUsuario);
}

