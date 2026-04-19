package com.GeoMarket.UsuarioLocal_Service.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.GeoMarket.UsuarioLocal_Service.model.UsuarioLocal;
import com.GeoMarket.UsuarioLocal_Service.repository.UsuarioLocalRepository;


@Service
public class UsuarioLocalService {

    private final UsuarioLocalRepository repository;

    public UsuarioLocalService(UsuarioLocalRepository repository){
        this.repository = repository;
    }


    // CREAR ASIGNACION
    public UsuarioLocal crearAsignacion(UsuarioLocal asignacion){

    if(repository.existsByIdUsuarioAndIdLocal(
        asignacion.getIdUsuario(),
        asignacion.getIdLocal())){
        throw new RuntimeException("Ya existe esta asignación");
    }
    asignacion.setFechaAsignacion(LocalDate.now());
    asignacion.setEstado("ACTIVO"); 

    return repository.save(asignacion);
}



    // LISTAR TODAS
    public List<UsuarioLocal> listar(){
        return repository.findAll();
    }



    // POR USUARIO
    public List<UsuarioLocal> obtenerPorUsuario(Long usuarioId){
        return repository.findByIdUsuario(usuarioId);
    }



    // POR LOCAL
    public List<UsuarioLocal> obtenerPorLocal(Long localId){
        return repository.findByIdLocal(localId);
    }



    // ELIMINAR
    public void eliminar(Long id){
        repository.deleteById(id);
    }
}