package com.GeoMarket.Favoritos_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GeoMarket.Favoritos_Service.model.Favorito;
import com.GeoMarket.Favoritos_Service.repository.FavoritoRepository;

import jakarta.transaction.Transactional;


@Service
public class FavoritoService {

     @Autowired
    private FavoritoRepository favoritorepository;



    //GUARDAR FAVORITO
    public Favorito guardarFavorito(Long idUsuario, Long idProducto) {

        if (idUsuario == null || idProducto == null) {
            throw new IllegalArgumentException("Datos inválidos");
        }

        if (favoritorepository.existsByIdUsuarioAndIdProducto(idUsuario, idProducto)) {
            throw new RuntimeException("El producto ya está en favoritos");
        }

        Favorito f = new Favorito();
        f.setIdUsuario(idUsuario);
        f.setIdProducto(idProducto);

        return favoritorepository.save(f);
    }





    //OBTENER RESULTADOS DE FAVORITO
    public List<Favorito> obtenerFavoritos(Long idUsuario) {

        if (idUsuario == null) {
            throw new IllegalArgumentException("Usuario inválido");
        }

        return favoritorepository.findByIdUsuario(idUsuario);
    }
        



    //ELIMINAR FAVORITO
    @Transactional
    public void eliminarFavorito(Long idUsuario, Long idProducto) {
        if (!favoritorepository.existsByIdUsuarioAndIdProducto(idUsuario, idProducto)) {
            throw new RuntimeException("El favorito no existe");
        }

        favoritorepository.deleteByIdUsuarioAndIdProducto(idUsuario, idProducto);
    }



    //VERIFICAR SI ES FAVORITO
    public boolean esFavorito(Long idUsuario, Long idProducto) {
        return favoritorepository.existsByIdUsuarioAndIdProducto(idUsuario, idProducto);
}






}

