package com.GeoMarket.Ubicacion_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GeoMarket.Ubicacion_Service.model.Comuna;
import com.GeoMarket.Ubicacion_Service.model.Local;
import com.GeoMarket.Ubicacion_Service.repository.ComunaRepository;
import com.GeoMarket.Ubicacion_Service.repository.LocalRepository;


@Service
public class ComunaService {


    @Autowired
    private ComunaRepository comunarepository;

    @Autowired
    private LocalRepository localrepository;

    //validaciones//
    //validar cualquier id
    private void validarId(Long id){
        if(id == null){
            throw new IllegalArgumentException("Id invalido");
        }
    }   

    private void validarNombre(String nombre){
        if(nombre == null|| nombre.isBlank()){
            throw new IllegalArgumentException("Nombre invalido");
        }
    }


    //trabajador//
    //obtener comuna por ciudad
    public List<Comuna> obtenerComunasPorCiudad(Long idCiudad){
        validarId(idCiudad);
        return comunarepository.findByCiudad_IdCiudad(idCiudad);
    }
    
    //OBTENER COMUNA POR ID
    public Comuna obtenerPorId(Long idComuna){
        validarId(idComuna);
        return comunarepository.findById(idComuna)
        .orElseThrow(() -> new RuntimeException("comuna no encontrado con id : " + idComuna));
    }


    //METODOS DE ADMIN
    //CREAR COMUNA
    public Comuna crearComuna(Comuna comuna){
        if(comuna== null ){
            throw new IllegalArgumentException("Comuna obligatoria");
        }
         validarNombre(comuna.getNombreComuna());
         return comunarepository.save(comuna);
    }

    //OBTENER LISTA DE COMUNAS
    public List<Comuna> listarComuna(){
        return comunarepository.findAll();
    }

    //ACTUALIZAR COMUNA POR ID COMUNA
    public Comuna actualizarComuna(Long idComuna, Comuna datos){
        validarId(idComuna);
        Comuna Existente = comunarepository.findById(idComuna)
        .orElseThrow(() -> new RuntimeException("comuna no encontrada"));

        if(datos.getNombreComuna() != null){
            validarNombre(datos.getNombreComuna());
            Existente.setNombreComuna(datos.getNombreComuna());
        }
        return comunarepository.save(Existente);
    }

    //ELIMIAR COMUNA POR ID COMUNA
    public void EliminarComuna(Long idComuna){
        validarId(idComuna);

        if(!comunarepository.existsById(idComuna)){
            throw new RuntimeException("comuna no existe");
        }
        comunarepository.deleteById(idComuna);
    }



  
    //CONTROL
    // CONTAR LOCALES EN UNA COMUNA
    public Long contarLocales(Long idComuna) {
        validarId(idComuna);
        return localrepository.countByComuna_IdComuna(idComuna);
    }
    //OBTENER LOCALES DE UNA COMUNA
    public List<Local> obtenerLocalesPorComuna(Long idComuna) {
        validarId(idComuna);
        return localrepository.findByComuna_IdComuna(idComuna);
    }

   
    //FILTROS
    // BUSCAR COMUNA POR NOMBRE
    public List<Comuna> buscarPorNombre(String nombre) {
        validarNombre(nombre);
        return comunarepository.findByNombreComunaContainingIgnoreCase(nombre);
    }

    //MÉTODO 
    public String resumenComuna(Long idComuna) {
        
        validarId(idComuna);
    
        Comuna comuna = obtenerPorId(idComuna);
        Long totalLocales = contarLocales(idComuna);

        if(totalLocales == 0){
            return "Comuna : " + comuna.getNombreComuna()
            + " no tiene locales registrado";
        }

        return "Comuna: " + comuna.getNombreComuna() +
               " | Total Locales: " + totalLocales;

        
    
    }



}


    

