package com.GeoMarket.Producto_Service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GeoMarket.Producto_Service.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    boolean existsByNombreMarca(String nombreMarca);

    Optional<Marca> findByNombreMarca(String nombreMarca);
}
