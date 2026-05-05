package com.GeoMarket.Ubicacion_Service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GeoMarket.Ubicacion_Service.model.Region;

@Repository
public interface RegionRepository extends  JpaRepository<Region, Long> {

     //BUSCAR REGION POR NOMBRE
    List<Region> findByNombreRegionContainingIgnoreCase(String nombreRegion);
}

