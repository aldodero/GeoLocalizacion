package Geomarket.Evento_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Geomarket.Evento_Service.model.Evento;
import Geomarket.Evento_Service.model.TipoEvento;
import Geomarket.Evento_Service.repository.EventoRepository;
import Geomarket.Evento_Service.repository.TipoEventoRepository;
import Geomarket.Evento_Service.service.client.LocalClient;
import Geomarket.Evento_Service.service.client.UsuarioClient;


@Service
public class EventoService {

    @Autowired
    private EventoRepository eventorepository;

    @Autowired 
    private TipoEventoRepository tipoeventorepository;

    @Autowired
    private UsuarioClient usuarioclient;
    
    @Autowired
    private LocalClient localclient;

 

    //listar eventos
    public List<Evento> listarEventos(){
        return eventorepository.findAll();
    }

    //listar enventos por local 
    public List<Evento> listarPorLocal(Long idLocal){
        return eventorepository.findByIdLocal(idLocal);
    }
    
    //listar evento por usuario y local
    public List<Evento> listarPorUsuarioYLocal(Long idUsuario, Long idLocal){
        return  eventorepository.findByIdUsuarioAndIdLocal(idUsuario, idLocal);
    }




    //crear un nuevo evento con validacion
    public Evento crear(Evento evento) {

    if (usuarioclient.obtenerUsuario(evento.getIdUsuario()) == null) {
        throw new RuntimeException("Usuario no existe");
    }
    if (localclient.obtenerLocal(evento.getIdLocal()) == null) {
        throw new RuntimeException("Local no existe");
    }
    Long idTipoEvento =evento.getTipoEvento().getIdTipoEvento();
    TipoEvento tipoEvento =tipoeventorepository.findById(idTipoEvento)
        .orElseThrow(() -> new RuntimeException("TipoEvento no encontrado"));

    evento.setTipoEvento(tipoEvento);
    if(evento.getNotificacionEnviada()==null){
        evento.setNotificacionEnviada(false);
    }
    
    return eventorepository.save(evento);
}




    //Eliminar evento por id 
    public String EliminarEvento(Long idEvento){
        if(!eventorepository.existsById(idEvento)){
            return "id no existe";
        }
        eventorepository.deleteById(idEvento);
        return "evento eliminado con exito";
    }


    
    //actualizar evento por id
    public String ActualizarInfoEvento(Long idEvento, Evento nuevaInfo){
        Evento existente = eventorepository.findById(idEvento)
            .orElseThrow(() ->   new IllegalArgumentException("id no existe"));

            if(nuevaInfo.getTitulo() != null){
                existente.setTitulo(nuevaInfo.getTitulo());
            }
            if(nuevaInfo.getDescripcion() != null){
                existente.setDescripcion(nuevaInfo.getDescripcion());
            }
            if (nuevaInfo.getFechaEvento() != null) { 
                existente.setFechaEvento(nuevaInfo.getFechaEvento()); 
            }
            if(nuevaInfo.getHoraEvento() != null){
                existente.setHoraEvento(nuevaInfo.getHoraEvento());
            }
            eventorepository.save(existente);
            return "evento actualizado con exito";
    }
}
