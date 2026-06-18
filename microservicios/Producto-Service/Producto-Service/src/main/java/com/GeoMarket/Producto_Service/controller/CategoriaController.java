package com.GeoMarket.Producto_Service.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GeoMarket.Producto_Service.model.Categoria;
import com.GeoMarket.Producto_Service.service.CategoriaService;



@RestController
@RequestMapping("/api/categorias")
@CrossOrigin("*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaservice;


    
    // LISTAR TODAS las categorias
    @GetMapping("/listar")
    public List<Categoria> listar() {
        return categoriaservice.obtenerCategorias();
    }

    // OBTENER categorias POR ID
    @GetMapping("/{idCategoria}")
    public Categoria obtenerPorId(@PathVariable Long idCategoria) {
        return categoriaservice.obtenerPorId(idCategoria);
    }
 
    // CREAR categorias
    @PostMapping("/guardar")
    public Categoria guardar(@RequestBody Categoria categoria) {
        return categoriaservice.guardarCategoria(categoria);
    }

    // ELIMINAR categorias
    @DeleteMapping("/eliminar/{idCategoria}")
    public String eliminar(@PathVariable Long idCategoria) {
        categoriaservice.eliminarCategoria(idCategoria);
        return "Categoría eliminada correctamente";
    }
}