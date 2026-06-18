package com.GeoMarket.UsuarioLocal_Service.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.GeoMarket.UsuarioLocal_Service.model.UsuarioLocal;
import com.GeoMarket.UsuarioLocal_Service.repository.UsuarioLocalRepository;
import com.GeoMarket.UsuarioLocal_Service.service.client.NotificationClient;


@Service
public class UsuarioLocalService {

    
    private final UsuarioLocalRepository usuariolocalrepository;

    private final NotificationClient notificationclient;


    public UsuarioLocalService(
    UsuarioLocalRepository repository,
    NotificationClient notificationClient
){
    this.usuariolocalrepository = repository;
    this.notificationclient =
            notificationClient;
}

    // CREAR ASIGNACION
    public UsuarioLocal crearAsignacion(UsuarioLocal asignacion){

    if(usuariolocalrepository.existsByIdUsuarioAndIdLocal(
        asignacion.getIdUsuario(),
        asignacion.getIdLocal())){
        throw new RuntimeException("Ya existe esta asignación");
    }
    asignacion.setFechaAsignacion(LocalDate.now());
    asignacion.setEstado("ACTIVO"); 

    UsuarioLocal guardado =
        usuariolocalrepository.save(asignacion);

    // NOTIFICACION NUEVO LOCAL
    notificationclient.crearNotificacion(

            asignacion.getIdUsuario(),

            "Se te asignó un nuevo local",

            3L
    );

    return guardado;
}



    // LISTAR TODAS
    public List<UsuarioLocal> listar(){
        return usuariolocalrepository.findAll();
    }



    // POR USUARIO
    public List<UsuarioLocal> obtenerPorUsuario(Long usuarioId){
        return usuariolocalrepository.findByIdUsuario(usuarioId);
    }



    // POR LOCAL
    public List<UsuarioLocal> obtenerPorLocal(Long localId){
        return usuariolocalrepository.findByIdLocal(localId);
    }



    // ELIMINAR
    public String eliminar(Long id){
        if(id== null || id<=0){
            return "id invalido";
        }
        if(!usuariolocalrepository.existsById(id)){
            return "id no existe";
        }
        usuariolocalrepository.deleteById(id);
        return "usuario local eliminado con exito";
    }

  
}