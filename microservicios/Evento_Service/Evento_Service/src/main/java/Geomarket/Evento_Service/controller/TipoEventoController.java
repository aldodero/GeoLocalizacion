package Geomarket.Evento_Service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Geomarket.Evento_Service.model.TipoEvento;
import Geomarket.Evento_Service.service.TipoEventoService;

@RestController
@RequestMapping("api/tipoEventos")
public class TipoEventoController {

    @Autowired
    private TipoEventoService tipoeventoservice;

    @GetMapping("/listar")
    public List<TipoEvento> listar(){
        return tipoeventoservice.listar();
    }

    //crear tipo
    @PostMapping("/crear")
    public TipoEvento crear(@RequestBody TipoEvento event){
        return tipoeventoservice.crear(event);
    }

    //eliminar tipo por id
    @DeleteMapping("/Eliminar/{idTipo}")
    public String EliminarTipo(@PathVariable Long idTipo){
        return tipoeventoservice.EliminarTipo(idTipo);
    }

    //Actualizar tipo por id
    @PutMapping("/Actualizar/{idTipo}")
    public String ActualizarTipo(@PathVariable Long idTipo, @RequestBody TipoEvento nuevaInfo){
        return tipoeventoservice.ActualizarTipo(idTipo, nuevaInfo);
    }
}
