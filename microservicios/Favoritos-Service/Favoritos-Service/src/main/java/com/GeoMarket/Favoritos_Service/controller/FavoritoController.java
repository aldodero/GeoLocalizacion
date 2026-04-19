package com.GeoMarket.Favoritos_Service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GeoMarket.Favoritos_Service.model.Favorito;
import com.GeoMarket.Favoritos_Service.service.FavoritoService;


@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {



    @Autowired
    private FavoritoService favoritoservice;


    //GUARDAR FAVORITO
    @PostMapping("/guardar")
    public Favorito guardar(@RequestParam Long idUsuario,@RequestParam Long idProducto) {
        return favoritoservice.guardarFavorito(idUsuario, idProducto);
    }


    // LISTAR FAVORITOS POR ID
    @GetMapping("/listar/{idUsuario}")
    public List<Favorito> obtener(@PathVariable("idUsuario") Long idUsuario) {
        return favoritoservice.obtenerFavoritos(idUsuario);
    }

    // ELIMINAR FAVORITO DE MODULO 
    @DeleteMapping("/eliminar/{idUsuario}/{idProducto}")
    public String eliminar(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idProducto") Long idProducto) {
        favoritoservice.eliminarFavorito(idUsuario, idProducto);
        return "Favorito eliminado";

    }


    
    //VERIFICAR SI EXISTE
    @GetMapping("/existe{idUsuario}")
    public boolean esFavorito(@RequestParam Long idUsuario,@RequestParam Long idProducto) {
        return favoritoservice.esFavorito(idUsuario, idProducto);
    }




}

