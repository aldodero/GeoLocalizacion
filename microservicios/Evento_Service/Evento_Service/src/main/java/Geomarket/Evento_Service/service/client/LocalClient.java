package Geomarket.Evento_Service.service.client;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocalClient {

    private final RestTemplate restTemplate =
            new RestTemplate();

    private final String URL =
            "http://localhost:8081/api/locales/Obtener-local/";

    public Map<String, Object> obtenerLocal(
            Long idLocal
    ) {

        try {

            ResponseEntity<Map<String, Object>> response =
                    restTemplate.exchange(
                            URL + idLocal,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<Map<String, Object>>() {}
                    );

            return response.getBody();

        } catch (org.springframework.web.client.RestClientException e) {

            System.out.println(
                    "ERROR LOCAL CLIENT: "
                            + e.getMessage()
            );

            return null;
        }
    }
}