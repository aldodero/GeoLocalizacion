package com.GeoMarket.Producto_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GeoMarket.Producto_Service.model.Marca;
import com.GeoMarket.Producto_Service.repository.MarcaRepository;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcarepository;

    // LISTAR TODAS
    public List<Marca> obtenerMarcas() {
        return marcarepository.findAll();
    }

    // OBTENER  marca POR ID
    public Marca obtenerPorId(Long idMarca) {
        return marcarepository.findById(idMarca).orElseThrow(() ->
        new RuntimeException("Marca no encontrada"));
    }

    // CREAR marca 
    public Marca guardarMarca(Marca marca) {
        if (marca == null) {
            throw new IllegalArgumentException("Marca inválida");
        }
        if (marca.getNombreMarca() == null
                || marca.getNombreMarca().isBlank()) {throw new IllegalArgumentException("Nombre obligatorio");
        }
        if (marcarepository.existsByNombreMarca(marca.getNombreMarca())) {
            throw new RuntimeException("La marca ya existe");
        }
        return marcarepository.save(marca);
    }


    // ELIMINAR
    public void eliminarMarca(Long idMarca) {
        if (!marcarepository.existsById(idMarca)) {
            throw new RuntimeException("Marca no encontrada");
        }
        marcarepository.deleteById(idMarca);
    }



    
}
