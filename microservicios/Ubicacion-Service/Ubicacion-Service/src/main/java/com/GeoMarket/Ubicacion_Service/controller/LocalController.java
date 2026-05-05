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

import com.GeoMarket.Ubicacion_Service.model.Local;
import com.GeoMarket.Ubicacion_Service.service.LocalService;

@RestController
@RequestMapping("/api/locales")
public class LocalController {

    @Autowired
    private LocalService localservice;

   
    // OBTENER LOCAL POR UBICACIÓN
    @GetMapping("/Obtener-ubicacion")
    public List<Local> obtenerPorUbicacion(@RequestParam Double lat, @RequestParam Double lng) {
        return localservice.obtenerPorUbicacion(lat, lng);
    }

    // OBTENER LOCALES CERCANOS
    @GetMapping("/Locales-cercanos")
    public List<Local> obtenerLocalesCercanos(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam Double distancia) {
        return localservice.obtenerLocalesCercanos(lat, lng, distancia);
    }

    // OBTENER LOCAL POR ID
    @GetMapping("/Obtener-local/{id}")
    public Local obtenerPorId(@PathVariable Long id) {
        return localservice.obtenerPorId(id);
    }

    // OBTENER LOCAL POR CÓDIGO
    @GetMapping("/Obtener-codigo/{codigo}")
    public Local obtenerPorCodigo(@PathVariable String codigo) {
        return localservice.obtenerPorCodigo(codigo);
    }

   
    // CREAR LOCAL
    @PostMapping("/crear")
    public Local crear(@RequestBody Local local) {
        return localservice.crearLocal(local);
    }

    // LISTAR LOCALES
    @GetMapping("/listar")
    public List<Local> listar() {
        return localservice.listarLocales();
    }

    // ACTUALIZAR LOCAL POR ID
    @PutMapping("/Actualizar/{id}")
    public Local actualizar(@PathVariable Long id, @RequestBody Local datos) {
        return localservice.actualizarLocal(id, datos);
    }

    // ELIMINAR LOCAL POR ID
    @DeleteMapping("/Eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        localservice.eliminarLocal(id);
        return "Local eliminado correctamente";
    }

    // BUSCAR LOCAL POR NOMBRE
    @GetMapping("/buscar")
    public List<Local> buscar(@RequestParam String nombre) {
        return localservice.buscarPorNombre(nombre);
    }

    // ==============================
    // 🔎 VALIDACIONES
    // ==============================

    //  VERIFICAR SI EXISTE LOCAL POR ID 
    @GetMapping("/existe/id/{id}")
    public Boolean existePorId(@PathVariable Long id) {
        return localservice.existePorId(id);
    }

    //VERIFICAR SI  EXISTE LOCAL POR CÓDIGO
    @GetMapping("/existe/codigo/{codigo}")
    public Boolean existePorCodigo(@PathVariable String codigo) {
        return localservice.existePorCodigo(codigo);
    }

 
    //RESUEMN LOCAL TE MUESTRA NOMBRE DIRECCION
    @GetMapping("/resumen-local/{id}")
    public String resumen(@PathVariable Long id) {
        return localservice.resumenLocal(id);
    }
}