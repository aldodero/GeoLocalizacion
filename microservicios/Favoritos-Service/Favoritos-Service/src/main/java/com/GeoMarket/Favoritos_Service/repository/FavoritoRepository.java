package com.GeoMarket.Favoritos_Service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GeoMarket.Favoritos_Service.model.Favorito;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    
    
  

    Optional<Favorito> findByIdUsuarioAndIdProducto(Long idUsuario, Long idProducto);


    void deleteByIdUsuarioAndIdProducto(Long idUsuario, Long idProducto);

    //flitro para filtrar favoritos por local y user
    //trea listado favoritos por user and local
    List<Favorito>findByIdUsuarioAndIdLocal(Long idUsuario, Long idLocal);

    boolean existsByIdUsuarioAndIdProducto(Long idUsuario, Long idProducto);
    
    boolean existsByIdUsuarioAndIdProductoAndIdLocal(Long idUsuario ,Long idProducto, Long idLocal);
}

