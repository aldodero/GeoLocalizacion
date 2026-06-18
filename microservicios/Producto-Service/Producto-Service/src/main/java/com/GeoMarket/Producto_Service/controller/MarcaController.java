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

import com.GeoMarket.Producto_Service.model.Marca;
import com.GeoMarket.Producto_Service.service.MarcaService;



@RestController
@RequestMapping("/api/marcas")
@CrossOrigin("*")
public class MarcaController {

    @Autowired
    private MarcaService marcaservice;

    // LISTAR TODAS las marcas
    @GetMapping("/listar")
    public List<Marca> listar() {
        return marcaservice.obtenerMarcas();
    }

    // OBTENER marca por id
    @GetMapping("/{idMarca}")
    public Marca obtenerPorId(@PathVariable Long idMarca) {
        return marcaservice.obtenerPorId(idMarca);
    }


    // CREAR marca
    @PostMapping("/guardar")
    public Marca guardar(@RequestBody Marca marca) {
        return marcaservice.guardarMarca(marca);
    }

    // ELIMINAR marca
    @DeleteMapping("/eliminar/{idMarca}")
    public String eliminar(@PathVariable Long idMarca) {
        marcaservice.eliminarMarca(idMarca);
        return "Marca eliminada correctamente";
    }
}