package com.GeoMarket.Ubicacion_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GeoMarket.Ubicacion_Service.model.Ciudad;
import com.GeoMarket.Ubicacion_Service.model.Comuna;
import com.GeoMarket.Ubicacion_Service.model.Local;
import com.GeoMarket.Ubicacion_Service.repository.CiudadRepository;
import com.GeoMarket.Ubicacion_Service.repository.ComunaRepository;
import com.GeoMarket.Ubicacion_Service.repository.LocalRepository;

@Service
public class CiudadService {

    @Autowired
    private CiudadRepository ciudadrepository;

    @Autowired
    private ComunaRepository comunarepository;

    @Autowired
    private LocalRepository localrepository;

    //validaciones id
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



    //trabajador
    // Obtener ciudades por región
    public List<Ciudad> obtenerCiudadesPorRegion(Long idRegion) {
        validarId(idRegion);
        return ciudadrepository.findByRegion_IdRegion(idRegion);
    }

    // Obtener ciudad por ID
    public Ciudad obtenerPorId(Long idCiudad) {
        validarId(idCiudad);
        return ciudadrepository.findById(idCiudad)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
    }



    //ADMIN//
    //CREAR CIUDAD
     public Ciudad crearCiudad(Ciudad ciudad) {
        if (ciudad == null) {
            throw new IllegalArgumentException("Ciudad obligatoria");
        }
        validarNombre(ciudad.getNombreCiudad());

        return ciudadrepository.save(ciudad);
    }

    //OBTENER LISTA DE TODDAS
    public List<Ciudad> obtenerTodas() {
        return ciudadrepository.findAll();
    }

    //ACTUALIZAR CIUDAD
    public Ciudad actualizarCiudad(Long idCiudad, Ciudad datos) {
        validarId(idCiudad);

        Ciudad existente = obtenerPorId(idCiudad);

        if (datos.getNombreCiudad() != null) {
            validarNombre(datos.getNombreCiudad());
            existente.setNombreCiudad(datos.getNombreCiudad());
        }
        return ciudadrepository.save(existente);
    }


    //ELIMINAR CIUDAD POR ID    
    public void eliminarCiudad(Long idCiudad) {

        validarId(idCiudad);

        if (!ciudadrepository.existsById(idCiudad)) {
            throw new RuntimeException("Ciudad no existe");
        }

        ciudadrepository.deleteById(idCiudad);
    }   




    // Contar comunas
    public Long contarComunas(Long idCiudad) {
        validarId(idCiudad);
        return comunarepository.countByCiudad_IdCiudad(idCiudad);
    }

    // Contar locales
    public Long contarLocales(Long idCiudad) {
        validarId(idCiudad);
        return localrepository.countByComuna_Ciudad_IdCiudad(idCiudad);
    }

    // Obtener comunas
    public List<Comuna> obtenerComunas(Long idCiudad) {
        validarId(idCiudad);
        return comunarepository.findByCiudad_IdCiudad(idCiudad);
    }

    // Obtener locales de toda la ciudad
    public List<Local> obtenerLocales(Long idCiudad) {
        validarId(idCiudad);
        return localrepository.findByComuna_Ciudad_IdCiudad(idCiudad);
    }


    //FILTROS//
    //BUSCAR CIUDAD POR NOMBRE
     public List<Ciudad> buscarPorNombre(String nombre) {
        validarNombre(nombre);
        return ciudadrepository.findByNombreCiudadContainingIgnoreCase(nombre);
     }

    
        //RESUMEN//
        public String resumenCiudad(Long idCiudad) {

        validarId(idCiudad);

        Ciudad ciudad = obtenerPorId(idCiudad);
        Long totalComunas = contarComunas(idCiudad);
        Long totalLocales = contarLocales(idCiudad);
        
        if(totalComunas == 0 ){
            return "Ciudad: " + ciudad.getNombreCiudad()
            +" no tiene comunas registradas";  }

        if(totalLocales == 0 ){
            return "Ciudad : " + ciudad.getNombreCiudad()
            +" Comunas:" + totalComunas + "sin locales registrados";
        }

        return "Ciudad: " + ciudad.getNombreCiudad()
                + " | Comunas: " + totalComunas
                + " | Locales: " + totalLocales;
    }





}





