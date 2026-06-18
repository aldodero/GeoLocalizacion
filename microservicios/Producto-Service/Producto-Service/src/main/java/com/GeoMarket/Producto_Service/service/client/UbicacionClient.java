package com.GeoMarket.Producto_Service.service.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UbicacionClient {


     private final RestTemplate restTemplate = new RestTemplate();

    private final String URL = "http://localhost:8081/api/locales/listar";

    public Object obtenerLocales() {
        return restTemplate.getForObject(URL, Object.class);
    }
}
