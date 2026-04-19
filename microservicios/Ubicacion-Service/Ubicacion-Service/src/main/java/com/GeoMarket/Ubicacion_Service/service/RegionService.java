package com.GeoMarket.Ubicacion_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GeoMarket.Ubicacion_Service.model.Ciudad;
import com.GeoMarket.Ubicacion_Service.model.Comuna;
import com.GeoMarket.Ubicacion_Service.model.Local;
import com.GeoMarket.Ubicacion_Service.model.Region;
import com.GeoMarket.Ubicacion_Service.repository.CiudadRepository;
import com.GeoMarket.Ubicacion_Service.repository.ComunaRepository;
import com.GeoMarket.Ubicacion_Service.repository.LocalRepository;
import com.GeoMarket.Ubicacion_Service.repository.RegionRepository;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionrepository;

    @Autowired
    private CiudadRepository ciudadrepository;

    @Autowired
    private ComunaRepository comunarepository;

    @Autowired
    private LocalRepository localrepository;
    

    //VALIDACIONES///
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre inválido");
        }
    }


    //TRABAJADOR
    //  LOBTENER TODAS LAS REGIONES
    public List<Region> obtenerTodas() {
        return regionrepository.findAll();
    }

    // OBTENER REGION POR ID
    public Region obtenerPorId(Long idRegion) {
        validarId(idRegion);
        return regionrepository.findById(idRegion)
                .orElseThrow(() -> new RuntimeException("Región no encontrada"));
    }


    //ADMIN//
    //CREAR UNA REGION
    public Region crearRegion(Region region) {
        if (region == null) {
            throw new IllegalArgumentException("Región obligatoria");
        }

        validarNombre(region.getNombreRegion());

        return regionrepository.save(region);
    }


    //ACTUALIZAR REGION POR ID
    public Region actualizarRegion(Long idRegion, Region datos) {

        validarId(idRegion);

        Region existente = obtenerPorId(idRegion);

        if (datos.getNombreRegion() != null) {
            validarNombre(datos.getNombreRegion());
            existente.setNombreRegion(datos.getNombreRegion());
        }

        return regionrepository.save(existente);
    }

    //ELIMINAR REGION POR ID
    public void eliminarRegion(Long idRegion) {

        validarId(idRegion);

        if (!regionrepository.existsById(idRegion)) {
            throw new RuntimeException("Región no existe");
        }

        regionrepository.deleteById(idRegion);
    }




    //CONTROL DE DATOS////
     // CONTAR CIUDADES POR REGION
    public Long contarCiudades(Long idRegion) {
        validarId(idRegion);
        return ciudadrepository.countByRegion_IdRegion(idRegion);
    }

    // CONTAR COMUNAS
    public Long contarComunas(Long idRegion) {
        validarId(idRegion);
        return comunarepository.countByCiudad_Region_IdRegion(idRegion);
    }

    // CONTAR LCOALES POR ID REGION
    public Long contarLocales(Long idRegion) {
        validarId(idRegion);
        return localrepository.countByComuna_Ciudad_Region_IdRegion(idRegion);
    }

    // OBTENER CIUDADES POR ID REGION
    public List<Ciudad> obtenerCiudades(Long idRegion) {
        validarId(idRegion);
        return ciudadrepository.findByRegion_IdRegion(idRegion);
    }

    // OBTENER COMUNAS POR ID REGION
    public List<Comuna> obtenerComunas(Long idRegion) {
        validarId(idRegion);
        return comunarepository.findByCiudad_Region_IdRegion(idRegion);

    }

    // OBTENER LOCALES POR ID REGION
    public List<Local> obtenerLocales(Long idRegion) {
        validarId(idRegion);
        return localrepository.findByComuna_Ciudad_Region_IdRegion(idRegion);
    }




    //FILTRO//
    //BUSCAR REGION POR NOMBRE
    public List<Region> buscarPorNombre(String nombre) {
        validarNombre(nombre);
        return regionrepository.findByNombreRegionContainingIgnoreCase(nombre);
    }


    
    //resumen region
    public String resumenRegion(Long idRegion) {

    validarId(idRegion);

    Region region = obtenerPorId(idRegion);
    Long totalCiudades = contarCiudades(idRegion);
    Long totalComunas = contarComunas(idRegion);
    Long totalLocales = contarLocales(idRegion);

    
    if (totalCiudades == 0) {
        return "Región: " + region.getNombreRegion() +
               " |  No tiene ciudades registradas";
    }

    
    if (totalComunas == 0) {
        return "Región: " + region.getNombreRegion() +
               " | Ciudades: " + totalCiudades +
               " | Sin comunas registradas";
    }


    if (totalLocales == 0) {
        return "Región: " + region.getNombreRegion() +
               " | Ciudades: " + totalCiudades +
               " | Comunas: " + totalComunas +
               " | Sin locales registrados";
    }

   
    return "Región: " + region.getNombreRegion()
            + " | Ciudades: " + totalCiudades
            + " | Comunas: " + totalComunas
            + " | Locales: " + totalLocales;
}


}
