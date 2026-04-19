package com.GeoMarket.Producto_Service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GeoMarket.Producto_Service.model.TipoProducto;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long>{

 

    

    //BUSCAR POR NOMBRE
    List<TipoProducto> findByNombreTipoProductoContainingIgnoreCase(String nombre);

    //VERIFICAR QUE EXISA POR NOMBRE
    boolean existsByNombreTipoProducto (String nombreTipoProducto);

   
    

    }

