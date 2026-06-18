package com.GeoMarket.UsuarioLocal_Service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GeoMarket.UsuarioLocal_Service.model.UsuarioLocal;
import com.GeoMarket.UsuarioLocal_Service.service.UsuarioLocalService;
import com.GeoMarket.UsuarioLocal_Service.service.client.LocalClient;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/Usuario-Local")
public class UsuarioLocalController {

    private final UsuarioLocalService usuariolocalservice;
    private final LocalClient localclient;

    //CONTRUCTOR
    public UsuarioLocalController(
        UsuarioLocalService service,
        LocalClient localClient
    ){
        this.usuariolocalservice = service;
        this.localclient = localClient;
    }

    // CREAR
    @PostMapping("/crear")
    public UsuarioLocal crear(@RequestBody UsuarioLocal asignacion){
        return usuariolocalservice.crearAsignacion(asignacion);
    }

    // LISTAR
    @GetMapping("/listar")
    public List<UsuarioLocal> listar(){
        return usuariolocalservice.listar();
    }


    // OBTENER LOCAL POR ID DE USUARIO
    @GetMapping("/Obtener-usuario/{id}")
    public List<Map<String, Object>> porUsuario(
            @PathVariable Long id
        ) {

            List<UsuarioLocal> lista =
                    usuariolocalservice.obtenerPorUsuario(id);

            return lista.stream().map(asignacion -> {

                Map<String, Object> map =
                        new HashMap<>();

                map.put(
                        "idLocal",
                        asignacion.getIdLocal()
                );

                map.put(
                        "estado",
                        asignacion.getEstado()
                );

                Map<String, Object> local =
                        localclient.obtenerLocal(
                                asignacion.getIdLocal()
                        );

                if (local != null) {

                    map.put(
                            "nombreLocal",
                            local.get("nombreLocal")
                    );

                    map.put(
                            "direccionLocal",
                            local.get("direccionLocal")
                    );

                    map.put(
                            "latitud",
                            local.get("latitud")
                    );

                    map.put(
                            "longitud",
                            local.get("longitud")
                    );
                }

                return map;

            }).toList();
        }



    // OBTENER USUARIO POR LOCAL
    @GetMapping("/Obtener-Usuario-local/{id}")
    public List<UsuarioLocal> porLocal(@PathVariable Long id){
        return usuariolocalservice.obtenerPorLocal(id);
    }


    
    // ELIMINAR LA RELACION ENTRE ID USUARIO Y IDLOCAL
    @DeleteMapping("/Eliminar/{id}")
    public String eliminar(@PathVariable Long id){
        return usuariolocalservice.eliminar(id);
    }
}