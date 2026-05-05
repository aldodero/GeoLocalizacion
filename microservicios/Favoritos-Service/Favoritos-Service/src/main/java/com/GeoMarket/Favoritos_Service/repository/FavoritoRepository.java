package com.GeoMarket.Favoritos_Service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GeoMarket.Favoritos_Service.model.Favorito;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    
    
    List<Favorito> findByIdUsuario(Long idUsuario);

    Optional<Favorito> findByIdUsuarioAndIdProducto(Long idUsuario, Long idProducto);

    boolean existsByIdUsuarioAndIdProducto(Long idUsuario, Long idProducto);

    void deleteByIdUsuarioAndIdProducto(Long idUsuario, Long idProducto);
}

