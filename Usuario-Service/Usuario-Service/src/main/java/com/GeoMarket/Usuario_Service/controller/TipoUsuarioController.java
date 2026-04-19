package com.GeoMarket.Usuario_Service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GeoMarket.Usuario_Service.model.TipoUsuario;
import com.GeoMarket.Usuario_Service.service.TipoUsuarioService;

@RestController
@RequestMapping("/api/tipos-usuario")
public class TipoUsuarioController {

    private final TipoUsuarioService service;

    public TipoUsuarioController(TipoUsuarioService service) {
        this.service = service;
    }

    // ==============================
    // 📦 CRUD
    // ==============================

    // CREAR
    @PostMapping("/crear")
    public TipoUsuario crear(@RequestBody TipoUsuario tipo) {
        return service.crearTipoUsuario(tipo);
    }

    // LISTAR TODOS
    @GetMapping("/listar")
    public List<TipoUsuario> listar() {
        return service.listar();
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public TipoUsuario obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    // ACTUALIZAR
    @PutMapping("/actualizar/{id}")
    public TipoUsuario actualizar(
            @PathVariable Long id,
            @RequestBody TipoUsuario tipo) {

        return service.actualizar(id, tipo);
    }

    // ELIMINAR
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "Tipo de usuario eliminado correctamente";
    }

    // ==============================
    // 🔎 EXTRA
    // ==============================

    // BUSCAR POR ROL
    @GetMapping("/rol")
    public TipoUsuario obtenerPorRol(@RequestParam String rol) {
        return service.obtenerPorRol(rol);
    }

    // EXISTE POR ROL
    @GetMapping("/existe")
    public Boolean existe(@RequestParam String rol) {
        return service.existePorRol(rol);
    }

    // CONTAR
    @GetMapping("/conteo")
    public Long contar() {
        return service.contar();
    }


    @PutMapping("/cambiar-estado/{id}")
    public TipoUsuario cambiarEstado(@PathVariable Long id, @RequestParam Boolean estado) {
        return service.cambiarEstado(id, estado);
    }



@GetMapping("/estado")
public List<TipoUsuario> obtenerPorEstado(@RequestParam Boolean estado) {
    return service.buscarPorEstado(estado);
}




    // RESUMEN
    @GetMapping("/resumen")
    public String resumen() {
        return service.resumen();
    }

    
}