package Geomarket.ProductoLocal_Service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Geomarket.ProductoLocal_Service.model.ProductoLocal;
@Repository
public interface ProductoLocalRepository extends JpaRepository<ProductoLocal, Long> {

    //listar local por id de local
    List<ProductoLocal> findByIdLocal(Long idLocal);

    List<ProductoLocal> findByIdProducto(Long idProducto);
    
    //contar productos por local
    Long countByIdLocal(Long idLocal);
    @Query("""
    SELECT SUM(p.stock)
    FROM ProductoLocal p
    WHERE p.idLocal = :idLocal
    """)
    Double sumarStockPorLocal(
            @Param("idLocal") Long idLocal
    );

    
}
