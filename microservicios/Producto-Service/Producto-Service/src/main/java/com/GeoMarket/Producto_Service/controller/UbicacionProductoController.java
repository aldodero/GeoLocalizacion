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

import com.GeoMarket.Producto_Service.model.UbicacionProducto;
import com.GeoMarket.Producto_Service.service.UbicacionProductoService;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionProductoController {

    @Autowired
    private UbicacionProductoService service;





    
    // CREAR UBICACIÓN
    @PostMapping("/crear")
    public UbicacionProducto crear(@RequestBody UbicacionProducto ubicacion) {
        return service.crear(ubicacion);
    }

    // LISTAR TODAS
    @GetMapping("/listar")
    public List<UbicacionProducto> listar() {
        return service.listar();
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public UbicacionProducto obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    // ACTUALIZAR
    @PutMapping("/actualizar/{id}")
    public UbicacionProducto actualizar(
            @PathVariable Long id,
            @RequestBody UbicacionProducto datos) {
        return service.actualizar(id, datos);
    }

    // ELIMINAR
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "Ubicación eliminada correctamente";
    }

   


    
    // BUSCAR UBICACIÓN POR CÓDIGO
    @GetMapping("/buscar")
    public UbicacionProducto buscar(@RequestParam String codigo) {
        return service.obtenerUbicacionPorCodigo(codigo);
    }

    // EXTO
    @GetMapping("/texto")
    public String texto(@RequestParam String codigo) {
        return service.obtenerUbicacionTexto(codigo);
    }

    // MAPA
    @GetMapping("/mapa")
    public String mapa(@RequestParam String codigo) {
        return service.ubicacionParaMapa(codigo);
    }

    




    @GetMapping("/pasillo")
    public List<UbicacionProducto> porPasillo(@RequestParam String pasillo) {
        return service.buscarPorPasillo(pasillo);
    }

    @GetMapping("/seccion")
    public List<UbicacionProducto> porSeccion(@RequestParam String seccion) {
        return service.buscarPorSeccion(seccion);
    }

    @GetMapping("/gondola")
    public List<UbicacionProducto> porGondola(@RequestParam String gondola) {
        return service.buscarPorGondola(gondola);
    }

   




    @GetMapping("/existe/{id}")
    public Boolean existe(@PathVariable Long id) {
        return service.existePorId(id);
    }

   


    @GetMapping("/conteo")
    public Long contar() {
        return service.contarUbicaciones();
    }

    @GetMapping("/resumen")
    public String resumen() {
        return service.resumen();
    }
}