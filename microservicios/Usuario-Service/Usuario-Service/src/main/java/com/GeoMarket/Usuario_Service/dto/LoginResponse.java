package com.GeoMarket.Usuario_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String correo;
    private String tipoUsuario;
}