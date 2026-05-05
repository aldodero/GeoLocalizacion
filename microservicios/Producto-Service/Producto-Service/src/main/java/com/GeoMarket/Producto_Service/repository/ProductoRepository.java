package com.GeoMarket.Producto_Service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GeoMarket.Producto_Service.model.Producto;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

    

    Optional<Producto> findByCodigoProducto(String codigoProducto);


    Optional<Producto> findByCodigoProductoIgnoreCase(String codigoProducto);

    boolean existsByCodigoProducto(String codigoProducto);

    List<Producto> findByNombreProductoContainingIgnoreCase(String nombreProducto);

    List<Producto> findByTipoProducto_IdTipoProducto(Long idTipoProducto);

    List<Producto> findByStock(int stock);

    List<Producto> findByStockLessThan(int stock);

    List<Producto> findByPrecioBetween(Double min, Double max);

    Long countByStock(int stock);
    
    Long countByTipoProducto_IdTipoProducto(Long idTipoProducto);
    

    }
