package com.GeoMarket.Producto_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GeoMarket.Producto_Service.model.Categoria;
import com.GeoMarket.Producto_Service.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriarepository;

    // LISTAR TODAS las categoria
    public List<Categoria> obtenerCategorias() {
        return categoriarepository.findAll();
    }

    // OBTENER categoria POR ID
    public Categoria obtenerPorId(Long idCategoria) {
        return categoriarepository.findById(idCategoria)
                .orElseThrow(() ->new RuntimeException(
                "Categoría no encontrada"));
    }

    // CREAR categoria
    public Categoria guardarCategoria(Categoria categoria) {
        if (categoria == null) {throw new IllegalArgumentException("Categoría inválida");
        }

        if (categoria.getNombreCategoria() == null
        || categoria.getNombreCategoria().isBlank()) {throw new IllegalArgumentException("Nombre obligatorio");
        }

        if (categoriarepository.existsByNombreCategoria(categoria.getNombreCategoria())) {
            throw new RuntimeException("La categoría ya existe" );
        }

        return categoriarepository.save(categoria);
    }




    // ELIMINAR categoria
    public void eliminarCategoria(Long idCategoria) {
        if (!categoriarepository.existsById(idCategoria)) {
            throw new RuntimeException("Categoría no encontrada");
        }

        categoriarepository.deleteById(idCategoria);
    }



}
