package com.GeoMarket.UsuarioLocal_Service.service.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationClient {

    private final RestTemplate restTemplate =
            new RestTemplate();

    private final String URL =
            "http://localhost:8086/api/notificaciones/crear";

    public void crearNotificacion(
        Long idUsuario,
        String mensaje,
        Long idTipo
) {

        try {
                HttpHeaders headers =
                        new HttpHeaders();

                headers.setContentType(
                        MediaType.APPLICATION_JSON
                );

                Map<String, Object> body =
                        new HashMap<>();

                body.put("idUsuario", idUsuario);
                body.put("mensaje", mensaje);
                body.put("idTipo", idTipo);

                HttpEntity<Map<String, Object>>
                        request =
                        new HttpEntity<>(
                                body,
                                headers
                        );

                restTemplate.postForObject(

                        URL,

                        request,

        String.class
);

        } catch (org.springframework.web.client.RestClientException e) {

                System.out.println(

                        "ERROR NOTIFICACION LOCAL: "
                                + e.getMessage()
                );
        }
}


}
