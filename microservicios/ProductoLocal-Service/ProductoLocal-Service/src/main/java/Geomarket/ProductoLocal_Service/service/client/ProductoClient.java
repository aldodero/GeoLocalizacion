package Geomarket.ProductoLocal_Service.service.client;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ProductoClient {

    private final RestTemplate restTemplate =
            new RestTemplate();

    private final String URL =
            "http://localhost:8080/api/productos/";


            //obtener producto desde productos
    public Map<String, Object> obtenerProducto(Long idProducto) {

        try {

            ResponseEntity<Map<String, Object>> response =
                    restTemplate.exchange(
                            URL + "Obtener/" + idProducto,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<Map<String, Object>>() {}
                    );

            return response.getBody();

        } catch (org.springframework.web.client.RestClientException e) {

            System.out.println(
                    "ERROR PRODUCTO CLIENT: " + e.getMessage()
            );

            return null;
        }
    }

    //buscar producto por codigo
    public Map<String, Object> buscarPorCodigo(
        String codigo
        ) {

        try {

                ResponseEntity<Map<String, Object>> response =
                        restTemplate.exchange(
                                URL + "buscar-codigo?codigo=" + codigo,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<Map<String, Object>>() {}
                        );

                return response.getBody();

        } catch (org.springframework.web.client.RestClientException e) {

                System.out.println(
                        "ERROR PRODUCTO CLIENT: " + e.getMessage()
                );

                return null;
                }
        }


}