package com.GeoMarket.Ubicacion_Service.controller;

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

import com.GeoMarket.Ubicacion_Service.model.Ciudad;
import com.GeoMarket.Ubicacion_Service.model.Comuna;
import com.GeoMarket.Ubicacion_Service.model.Local;
import com.GeoMarket.Ubicacion_Service.model.Region;
import com.GeoMarket.Ubicacion_Service.service.RegionService;

@RestController
@RequestMapping("/api/regiones")
public class RegionController {

    @Autowired
    private RegionService regionservice;





    //TRABAJADOR//
    //OBTENER LISTA DE TODAS LAS REGIONES
    @GetMapping("/lista")
    public List<Region> obtenerTodas() {
        return regionservice.obtenerTodas();
    }

    // OBTENER REGION POR ID
    @GetMapping("/Obtener-region{id}")
    public Region obtenerPorId(@PathVariable Long id) {
        return regionservice.obtenerPorId(id);
    }

   





    //ADMIN//
    // CREAR REGION
    @PostMapping("/crear")
    public Region crear(@RequestBody Region region) {
        return regionservice.crearRegion(region);
    }

    //ACTUALIZAR REGION
    @PutMapping("/Actualizar/{id}")
    public Region actualizar(@PathVariable Long id, @RequestBody Region datos) {
        return regionservice.actualizarRegion(id, datos);
    }

    // ELIMINAR REGION
    @DeleteMapping("/Eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        regionservice.eliminarRegion(id);
        return "Región eliminada correctamente";
    }



    //CONTROL DATOS//
    // CONTAR CIUDADES
    @GetMapping("/ciudades/conteo/{id}")
    public Long contarCiudades(@PathVariable Long id) {
        return regionservice.contarCiudades(id);
    }

    // CONTAR COMUNAS
    @GetMapping("/comunas/conteo/{id}")
    public Long contarComunas(@PathVariable Long id) {
        return regionservice.contarComunas(id);
    }

    // CONTAR LOCALES
    @GetMapping("/locales/conteo/{id}")
    public Long contarLocales(@PathVariable Long id) {
        return regionservice.contarLocales(id);
    }

    // OBTENER CIUDADES
    @GetMapping("/Obtener-ciudades/{id}")
    public List<Ciudad> obtenerCiudades(@PathVariable Long id) {
        return regionservice.obtenerCiudades(id);
    }

    // OBTENER COMUNAS
    @GetMapping("/Obtener-comunas/{id}")
    public List<Comuna> obtenerComunas(@PathVariable Long id) {
        return regionservice.obtenerComunas(id);
    }

    //OBTENER LOCALES
    @GetMapping("/Obtener-locales/{id}")
    public List<Local> obtenerLocales(@PathVariable Long id) {
        return regionservice.obtenerLocales(id);
    }

    





    //FILTRO//
    // BUSCAR REGION POR NOMBRE
    @GetMapping("/buscar")
    public List<Region> buscar(@RequestParam String nombre) {
        return regionservice.buscarPorNombre(nombre);
    }









  
    //RESUMEN GENERNAL//
    @GetMapping("/resumen-region/{id}")
    public String resumen(@PathVariable Long id) {
        return regionservice.resumenRegion(id);
    }
}