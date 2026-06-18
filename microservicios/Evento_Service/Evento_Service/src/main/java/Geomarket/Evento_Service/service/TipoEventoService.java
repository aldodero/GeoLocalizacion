package Geomarket.Evento_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Geomarket.Evento_Service.model.TipoEvento;
import Geomarket.Evento_Service.repository.TipoEventoRepository;


@Service
public class TipoEventoService {

    @Autowired TipoEventoRepository tipoeventorepository;

    //listar tipos de eventos
    public List<TipoEvento> listar(){
        return tipoeventorepository.findAll();
    }

    //crear tipo evento
    public TipoEvento crear(TipoEvento event){
        return tipoeventorepository.save(event);
    }

    //eliminar tipo de evento por id
    public String EliminarTipo(Long idTipo){

        if(!tipoeventorepository.existsById(idTipo)){
            return "id no existe";
        }
        tipoeventorepository.deleteById(idTipo);
        return "tipo eliminado con exito";
    }

    //actulizar tipo por id
    public String ActualizarTipo(Long idTipo, TipoEvento nuevaInfo){
        TipoEvento existente = tipoeventorepository.findById(idTipo)
        .orElseThrow(() -> new IllegalArgumentException("id no existe"));

        if(nuevaInfo.getNombreTipoEvento() != null){
            existente.setNombreTipoEvento(nuevaInfo.getNombreTipoEvento());
        }

        if(nuevaInfo.getDescripcion() != null){
            existente.setDescripcion(nuevaInfo.getDescripcion());
        }

        tipoeventorepository.save(existente);
        return "tipo evento actualizado con exito";
    }












}
