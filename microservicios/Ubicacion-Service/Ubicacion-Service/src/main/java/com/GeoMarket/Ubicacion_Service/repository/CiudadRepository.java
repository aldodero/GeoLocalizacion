package com.GeoMarket.Ubicacion_Service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GeoMarket.Ubicacion_Service.model.Ciudad;

@Repository
public interface CiudadRepository  extends JpaRepository<Ciudad, Long>{

// 🔥BUSCAR CIUDAD POR NOMBRE
    List<Ciudad> findByNombreCiudadContainingIgnoreCase(String nombreCiudad);

    // OBTENER CIUDAD POR REGION
    List<Ciudad> findByRegion_IdRegion(Long idRegion);
    

     // CONTAR CIUDADES POR REGION
    Long countByRegion_IdRegion(Long idRegion);

    
}
