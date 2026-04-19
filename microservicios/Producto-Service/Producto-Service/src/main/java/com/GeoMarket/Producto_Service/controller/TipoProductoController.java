package com.GeoMarket.Producto_Service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GeoMarket.Producto_Service.model.TipoProducto;
import com.GeoMarket.Producto_Service.service.TipoProductoService;

@RestController
@RequestMapping("/api/tipo-producto")
public class TipoProductoController {

    @Autowired
    private TipoProductoService tipoProductoService;

    


    // CREAR TIPO
    @PostMapping("/crear")
    public TipoProducto crear(@RequestBody TipoProducto tipo) {
        return tipoProductoService.crearTipo(tipo);
    }



    // LISTAR TODOS
    @GetMapping("/listar")
    public List<TipoProducto> listar() {
        return tipoProductoService.listarTipos();
    }



    // OBTENER POR ID
    @GetMapping("/{id}")
    public TipoProducto obtener(@PathVariable Long id) {
        return tipoProductoService.obtenerPorId(id);
    }



    // ACTUALIZAR
    @PutMapping("/actualizar/{id}")
    public TipoProducto actualizar(@PathVariable Long id, @RequestBody TipoProducto tipo) {
        return tipoProductoService.actualizarTipo(id, tipo);
    }



    // ELIMINAR
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        tipoProductoService.eliminarTipo(id);
        return "Tipo de producto eliminado correctamente";
    }

    


    // BUSCAR POR NOMBRE
    @GetMapping("/buscar")
    public List<TipoProducto> buscar(@RequestParam String nombre) {
        return tipoProductoService.buscarPorNombre(nombre);
    }

    



    // EXISTE POR ID
    @GetMapping("/existe/{id}")
    public Boolean existePorId(@PathVariable Long id) {
        return tipoProductoService.existePorId(id);
    }




    // EXISTE POR NOMBRE
    @GetMapping("/existe-nombre")
    public Boolean existePorNombre(@RequestParam String nombre) {
        return tipoProductoService.existePorNombre(nombre);
    }

   


    // CONTAR TIPOS
    @GetMapping("/conteo")
    public Long contar() {
        return tipoProductoService.contarTipos();
    }


    
    // RESUMEN
    @GetMapping("/resumen")
    public String resumen() {
        return tipoProductoService.resumen();
    }
}