package com.GeoMarket.Producto_Service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GeoMarket.Producto_Service.model.Producto;
import com.GeoMarket.Producto_Service.service.ProductoService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    @GetMapping("/completo")
public List<Map<String, Object>> buscarCompleto(@RequestParam String nombre) {

    List<Producto> productos = productoService.buscarPorNombre(nombre);

    return productos.stream().map(p -> {

        Map<String, Object> result = new HashMap<>();

        result.put("nombre", p.getNombreProducto()); 
        result.put("codigo", p.getCodigoProducto()); 

        // después conectas microservicio ubicación)
        result.put("pasillo", 5);
        result.put("ubicacion", "Bebidas");
        result.put("x", 150);
        result.put("y", 200);

        return result;

    }).toList();
}
    
    // CREAR PRODUCTO
    @PostMapping("/crear")
    public Producto crear(@RequestBody Producto producto) {
        return productoService.crearProducto(producto);
    }

    


    // LISTAR TODOS LOS PRODUCTOS
    @GetMapping("/listar")
    public List<Producto> listar() {
        return productoService.listarProductos();
    }




    // ACTUALIZAR PRODUCTO
    @PutMapping("/actualizar/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizarProducto(id, producto);
    }



    // ELIMINAR PRODUCTO
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "Producto eliminado correctamente";
    }




    //ESCANEO PRINCIPAL DE PRODUCTO 
    @GetMapping("/escanear")
    public Producto escanear(@RequestParam String codigo) {
        return productoService.escanearProducto(codigo);
    }




    // BUSCAR POR CÓDIGO
    @GetMapping("/buscar-codigo")
    public Producto buscarPorCodigo(@RequestParam String codigo) {
        return productoService.buscarPorCodigo(codigo);
    }



    // BUSCAR POR NOMBRE
    @GetMapping("/buscar-nombre")
    public List<Producto> buscarPorNombre(@RequestParam String nombre) {
        return productoService.buscarPorNombre(nombre);
    }

   



    // BUSCAR POR TIPO
    @GetMapping("/tipo/{idTipo}")
    public List<Producto> buscarPorTipo(@PathVariable Long idTipo) {
        return productoService.buscarPorTipo(idTipo);
    }



    // PRODUCTOS SIN STOCK
    @GetMapping("/sin-stock")
    public List<Producto> sinStock() {
        return productoService.productosSinStock();
    }



    // PRODUCTOS BAJO STOCK
    @GetMapping("/bajo-stock")
    public List<Producto> bajoStock(@RequestParam int limite) {
        return productoService.productosBajoStock(limite);
    }




    // PRODUCTOS POR RANGO DE PRECIO
    @GetMapping("/rango-precio")
    public List<Producto> rangoPrecio(@RequestParam Double min, @RequestParam Double max) {
        return productoService.productosPorRangoPrecio(min, max);
    }

   



    // EXISTE POR ID
    @GetMapping("/existe-id/{id}")
    public Boolean existePorId(@PathVariable Long id) {
        return productoService.existePorId(id);
    }



    // EXISTE POR CÓDIGO
    @GetMapping("/existe-codigo")
    public Boolean existePorCodigo(@RequestParam String codigo) {
        return productoService.existePorCodigo(codigo);
    }




    // CONTAR PRODUCTOS
    @GetMapping("/conteo")
    public Long contar() {
        return productoService.contarProductos();
    }



    // CONTAR SIN STOCK
    @GetMapping("/conteo/sin-stock")
    public Long contarSinStock() {
        return productoService.contarSinStock();
    }



    // CONTAR POR TIPO
    @GetMapping("/conteo/tipo/{idTipo}")
    public Long contarPorTipo(@PathVariable Long idTipo) {
        return productoService.contarPorTipo(idTipo);
    }



    
    // RESUMEN GENERAL
    @GetMapping("/resumen")
    public String resumen() {
        return productoService.resumenGeneral();
    }
}