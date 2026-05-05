package com.GeoMarket.Ubicacion_Service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GeoMarket.Ubicacion_Service.model.Comuna;


@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {
    
    //BUSCAR COMUNA POR NOMBRE
    List<Comuna> findByNombreComunaContainingIgnoreCase(String nombreComuna);

     //CONTAR COMUNAS POR CIUDAD
    Long countByCiudad_IdCiudad(Long idCiudad);

    // OBTENER COMUNAS POR CIUDAD
    List<Comuna> findByCiudad_IdCiudad(Long idCiudad);




    //CONTAR COMUNAS POR REGION
    Long countByCiudad_Region_IdRegion(Long idRegion);

    //OBTENER COMUNAS POR REGION
    List<Comuna> findByCiudad_Region_IdRegion(Long IdRegion);
}
