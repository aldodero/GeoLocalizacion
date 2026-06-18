package Geomarket.Evento_Service.service.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificacionClient {

    private final RestTemplate restTemplate =
            new RestTemplate();

    private final String URL =
            "http://localhost:8086/api/notificaciones/crear";

        public void crearNotificacion(
                Long idUsuario,
                Long idLocal,
                String titulo,
                String descripcion,
                Long idTipo
        ) {

        try {

                Map<String, Object> body =
                        new HashMap<>();

                body.put(
                        "idUsuario",
                        idUsuario
                );

                body.put(
                        "idLocal",
                        idLocal
                );

                body.put(
                        "titulo",
                        titulo
                );

                body.put(
                        "descripcion",
                        descripcion
                );

                body.put(
                        "idTipo",
                        idTipo
                );

                ResponseEntity<Object>
                        response =
                        restTemplate.postForEntity(
                                URL,
                                body,
                                Object.class
                        );

                System.out.println(
                        "NOTIFICACION ENVIADA: "
                                + response.getStatusCode()
                );

        } catch (org.springframework.web.client.RestClientException e) {

                System.out.println(
                        "ERROR NOTIFICACION CLIENT: "
                                + e.getMessage()
                );
        }
  }
}