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
import com.GeoMarket.Ubicacion_Service.service.CiudadService;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadservice;

    

    //TRABAJADOR//
    // OBTENER CIUDADES POR ID DE REGION
    @GetMapping("/Obtener-ciudad-region/{idRegion}")
    public List<Ciudad> obtenerPorRegion(@PathVariable Long idRegion) {
        return ciudadservice.obtenerCiudadesPorRegion(idRegion);
    }

    // Obtener ciudad por ID
    @GetMapping("/Obtener-ciudad/{id}")
    public Ciudad obtenerPorId(@PathVariable Long id) {
        return ciudadservice.obtenerPorId(id);
    }

    //ASMIN//
    // Crear ciudad
    @PostMapping("/crear")
    public Ciudad crear(@RequestBody Ciudad ciudad) {
        return ciudadservice.crearCiudad(ciudad);
    }

    // Listar todas las ciudades
    @GetMapping("/listar")
    public List<Ciudad> listar() {
        return ciudadservice.obtenerTodas();
    }

    // Actualizar ciudad
    @PutMapping("/Actualizar/{id}")
    public Ciudad actualizar(@PathVariable Long id, @RequestBody Ciudad datos) {
        return ciudadservice.actualizarCiudad(id, datos);
    }

    // Eliminar ciudad
    @DeleteMapping("/Eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        ciudadservice.eliminarCiudad(id);
        return "Ciudad eliminada correctamente";
    }

    
    //CONTROL DATOS//
    // Contar comunas poor id de ciudad
    @GetMapping("/comunas/conteo/{id}")
    public Long contarComunas(@PathVariable Long id) {
        return ciudadservice.contarComunas(id);
    }

    // Contar locales por id ciudad 
    @GetMapping("/locales/conteo/{id}")
    public Long contarLocales(@PathVariable Long id) {
        return ciudadservice.contarLocales(id);
    }

    // Obtener comunas por id ciudad 
    @GetMapping("/Obtener-comunas/{id}")
    public List<Comuna> obtenerComunas(@PathVariable Long id) {
        return ciudadservice.obtenerComunas(id);
    }

    // Obtener locales por id ciuadd 
    @GetMapping("/Obtener-locales/{id}")
    public List<Local> obtenerLocales(@PathVariable Long id) {
        return ciudadservice.obtenerLocales(id);
    }

  
    // Buscar ciudad por nombre 
    @GetMapping("/buscar")
    public List<Ciudad> buscar(@RequestParam String nombre) {
        return ciudadservice.buscarPorNombre(nombre);
    }


    //resumen ciudad 
    @GetMapping("/resumen-ciudad/{id}")
    public String resumen(@PathVariable Long id) {
        return ciudadservice.resumenCiudad(id);
    }
}