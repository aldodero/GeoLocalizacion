package com.GeoMarket.Usuario_Service.controller;

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

import com.GeoMarket.Usuario_Service.dto.LoginRequest;
import com.GeoMarket.Usuario_Service.dto.LoginResponse;
import com.GeoMarket.Usuario_Service.model.Usuario;
import com.GeoMarket.Usuario_Service.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;




    @PostMapping("/crear")
    public Usuario crear(@RequestBody Usuario usuario,@RequestParam Long tipoUsuarioId) {
        return service.crearUsuario(usuario, tipoUsuarioId);
    }


    @GetMapping("/obtener/{id}")
    public Usuario obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }



    @GetMapping("/listar")
    public List<Usuario> listar() {
        return service.listar();
    }


    @PutMapping("/actualizar/{id}")
    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return service.actualizar(id, usuario);
    }


    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "Usuario eliminado";
    }

    
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return service.login(request);
    }



     @GetMapping("/resumen")
    public String resumen() {
        return service.resumen();
    }


}