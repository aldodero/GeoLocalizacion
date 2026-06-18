package Geomarket.Evento_Service.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Geomarket.Evento_Service.model.Evento;
public interface  EventoRepository extends JpaRepository<Evento,Long>{

//evento por local 
List<Evento> findByIdLocal(Long idLocal);

 //evento por usuario y local
List<Evento> findByIdUsuarioAndIdLocal(Long idUsuario, Long idLocal);

List<Evento> findByFechaEventoAndNotificacionEnviada(LocalDate fechaEvento,Boolean notificacionEnviada);


}
