package com.GeoMarket.UsuarioLocal_Service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GeoMarket.UsuarioLocal_Service.model.UsuarioLocal;
import com.GeoMarket.UsuarioLocal_Service.service.UsuarioLocalService;

@RestController
@RequestMapping("/api/Usuario-Local")
public class UsuarioLocalController {

    private final UsuarioLocalService service;

    public UsuarioLocalController(UsuarioLocalService service){
        this.service = service;
    }

    // CREAR
    @PostMapping("/crear")
    public UsuarioLocal crear(@RequestBody UsuarioLocal asignacion){
        return service.crearAsignacion(asignacion);
    }

    // LISTAR
    @GetMapping("/listar")
    public List<UsuarioLocal> listar(){
        return service.listar();
    }

    // OBTENER POR USUARIO
    @GetMapping("/Obtener-usuario/{id}")
    public List<UsuarioLocal> porUsuario(@PathVariable Long id){
        return service.obtenerPorUsuario(id);
    }

    // OBTENER USUARIO POR LOCAL
    @GetMapping("/Obtener-Usuario-local/{id}")
    public List<UsuarioLocal> porLocal(@PathVariable Long id){
        return service.obtenerPorLocal(id);
    }

    // ELIMINAR LA RELACION ENTRE ID USUARIO Y IDLOCAL
    @DeleteMapping("/Eliminar/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}