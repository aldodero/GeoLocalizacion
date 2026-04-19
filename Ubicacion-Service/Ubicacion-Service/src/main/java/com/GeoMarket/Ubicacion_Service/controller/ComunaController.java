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

import com.GeoMarket.Ubicacion_Service.model.Comuna;
import com.GeoMarket.Ubicacion_Service.model.Local;
import com.GeoMarket.Ubicacion_Service.service.ComunaService;

@RestController
@RequestMapping("/api/comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaservice;

    //TRABAJADOR//
    // OBTENER COMUNAS POR ID DE CIUDAD
    @GetMapping("/ciudad/{idCiudad}")
    public List<Comuna> obtenerPorCiudad(@PathVariable Long idCiudad) {
        return comunaservice.obtenerComunasPorCiudad(idCiudad);
    }

    // OBTNER COMUNA POR ID 
    @GetMapping("/Obtener-Comuna/{id}")
    public Comuna obtenerPorId(@PathVariable Long id) {
        return comunaservice.obtenerPorId(id);
    }

   
    // ADMIN//
    // CREAR COMUNA
    @PostMapping("/crear")
    public Comuna crear(@RequestBody Comuna comuna) {
        return comunaservice.crearComuna(comuna);
    }

    // LISTAR TODAS LAS COMUNAS
    @GetMapping("/listar")
    public List<Comuna> listar() {
        return comunaservice.listarComuna();
    }

    //ACTUALIZAR COMUNA POR ID
    @PutMapping("/Actualizar/{id}")
    public Comuna actualizar(@PathVariable Long id, @RequestBody Comuna datos) {
        return comunaservice.actualizarComuna(id, datos);
    }

    // ELIMINAR COMUNA POR ID
    @DeleteMapping("/Eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        comunaservice.EliminarComuna(id);
        return "Comuna eliminada correctamente";
    }



    //CONTROL//
    //CONTAR LOCALES POR ID COMUNA
    @GetMapping("/locales/conteo/{id}")
    public Long contarLocales(@PathVariable Long id) {
        return comunaservice.contarLocales(id);
    }

    //OBTENER LOCALES POR  ID COMUNA
    @GetMapping("/Obtener-locales-Comuna/{id}")
    public List<Local> obtenerLocales(@PathVariable Long id) {
        return comunaservice.obtenerLocalesPorComuna(id);
    }




    //FILTRO//
    //BUSCAR COMUNA POR NOMBRE
    @GetMapping("/buscar")
    public List<Comuna> buscar(@RequestParam String nombre) {
        return comunaservice.buscarPorNombre(nombre);
    }
   
    //RESUMEN
    @GetMapping("/resumen-comuna/{id}")
    public String resumen(@PathVariable Long id) {
        return comunaservice.resumenComuna(id);
    }
}