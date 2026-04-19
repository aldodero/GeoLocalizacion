package com.GeoMarket.Usuario_Service.service;

import org.springframework.stereotype.Service;

@Service
public class JwtService {


    public String generarToken(String correo){
        return "token-" + correo;
    }
}
