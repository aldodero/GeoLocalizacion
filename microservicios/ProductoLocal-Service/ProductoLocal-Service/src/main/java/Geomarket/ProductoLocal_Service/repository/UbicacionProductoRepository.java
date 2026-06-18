package Geomarket.ProductoLocal_Service.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Geomarket.ProductoLocal_Service.model.UbicacionProducto;







@Repository
public interface UbicacionProductoRepository extends JpaRepository<UbicacionProducto, Long> {

    // 🔍 buscar por pasillo
    List<UbicacionProducto> findByPasilloIgnoreCase(String pasillo);

    // 🔍 buscar por sección
    List<UbicacionProducto> findBySeccionIgnoreCase(String seccion);

    // 🔍 buscar por góndola
    List<UbicacionProducto> findByGondolaIgnoreCase(String gondola);

    // 🔍 búsqueda combinada 
    List<UbicacionProducto> findByPasilloAndSeccionAndGondola(String pasillo,String seccion,String gondola
    );
    //buscar producto por local y por id de producto
    List<UbicacionProducto> findByProductolocal_IdProducto(Long idProducto);

    List<UbicacionProducto> findByProductolocal_IdProductoAndProductolocal_IdLocal(Long idProducto,Long idLocal
);

}