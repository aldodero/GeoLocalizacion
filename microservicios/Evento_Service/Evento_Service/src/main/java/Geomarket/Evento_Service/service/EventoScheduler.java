package Geomarket.Evento_Service.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import Geomarket.Evento_Service.model.Evento;
import Geomarket.Evento_Service.repository.EventoRepository;
import Geomarket.Evento_Service.service.client.NotificacionClient;

@Component
public class EventoScheduler {

    @Autowired
    private EventoRepository eventorepository;

    @Autowired
    private NotificacionClient notificacionClient;

    @Scheduled(cron = "0 * * * * *")
    public void revisarEventos() {

        LocalDate hoy = LocalDate.now();

        List<Evento> eventos =eventorepository.findByFechaEventoAndNotificacionEnviada(
                        hoy,
                        false
                );

        for (Evento evento : eventos) {

            try {

                notificacionClient.crearNotificacion(
                        evento.getIdUsuario(),
                        evento.getIdLocal(),
                        evento.getTitulo(),
                        evento.getDescripcion(),
                        4L
                        
                );

                evento.setNotificacionEnviada(true);

                eventorepository.save(evento);

                System.out.println(
                        "NOTIFICACION ENVIADA EVENTO "
                                + evento.getIdEvento()
                );

            } catch (Exception e) {

                System.out.println(
                        "ERROR ENVIANDO NOTIFICACION: "
                                + e.getMessage()
                );
            }
        }
    }
}