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

import Geomarket.Evento_Service.model.Evento;
import Geomarket.Evento_Service.service.EventoService;



@RestController
@RequestMapping("/api/Eventos")
public class EventoController {

    @Autowired
    private EventoService eventoservice;


    //listar todos los eventos
    @GetMapping("/listar")
    public List<Evento> listar(){
        return eventoservice.listarEventos();
    }

    //listar evento usuario y local
    @GetMapping("/listar/{idUsuario}/{idLocal}")
    public List<Evento> listarPorUsuarioYLocal(@PathVariable Long idUsuario, @PathVariable Long idLocal){
        return eventoservice.listarPorUsuarioYLocal(idUsuario, idLocal);
    }


    //crear evento
    @PostMapping("/crear")
    public Evento crear(@RequestBody Evento evento){
    return eventoservice.crear(evento);
}

    //eliminar un evento por id
    @DeleteMapping("/Eliminar/{idEvento}")
    public String EliminarEvento(@PathVariable Long idEvento){
        return eventoservice.EliminarEvento(idEvento);
    }

    //actualizar evento por id
    @PutMapping("/Actualizar/{idEvento}")
    public String ActualizarEvento(@PathVariable Long idEvento,@RequestBody Evento nuevaInfo){
        return eventoservice.ActualizarInfoEvento(idEvento, nuevaInfo);
    }


}
