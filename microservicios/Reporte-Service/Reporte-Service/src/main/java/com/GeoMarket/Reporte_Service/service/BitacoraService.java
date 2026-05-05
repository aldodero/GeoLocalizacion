package com.GeoMarket.Reporte_Service.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GeoMarket.Reporte_Service.model.Bitacora;
import com.GeoMarket.Reporte_Service.repository.BitacoraRepository;

@Service
public class BitacoraService {

    @Autowired
    private BitacoraRepository bitacorarepository;



    //VALIDACIONES
    private void validarUsuario(Long idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            throw new IllegalArgumentException("Usuario inválido");
        }
    }

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(campo + " inválido");
        }
    }
    

    //REGISTRO ACCION
    public Bitacora registrarAccion(String accion, String tabla, Long idUsuario) {

        validarUsuario(idUsuario);
        validarTexto(accion, "Acción");
        validarTexto(tabla, "Tabla");

        Bitacora b = new Bitacora();
        b.setAccion(accion.toUpperCase());
        b.setTablaAfectada(tabla.toLowerCase());
        b.setFechaCambio(LocalDate.now());
        b.setHoraCambio(LocalDateTime.now());
        b.setIdUsuario(idUsuario);

        return bitacorarepository.save(b);
    }


    //ACCIONES ESPECIFICAS///
    public void registrarCreacion(String tabla, Long idUsuario) {
        registrarAccion("CREAR", tabla, idUsuario);
    }

    public void registrarActualizacion(String tabla, Long idUsuario) {
        registrarAccion("ACTUALIZAR", tabla, idUsuario);
    }

    public void registrarEliminacion(String tabla, Long idUsuario) {
        registrarAccion("ELIMINAR", tabla, idUsuario);
    }

    public void registrarLogin(Long idUsuario) {
        registrarAccion("LOGIN", "usuario", idUsuario);
    }

    public void registrarActividadInusual(Long idUsuario) {
        registrarAccion("ACTIVIDAD_INUSUAL", "sistema", idUsuario);
    }

    

    //CONSULTAS ADMIN//
    public List<Bitacora> obtenerTodo() {
        return bitacorarepository.findAll();
    }

    public List<Bitacora> obtenerPorUsuario(Long idUsuario) {
        validarUsuario(idUsuario);
        return bitacorarepository.findByIdUsuario(idUsuario);
    }

    public List<Bitacora> obtenerPorTabla(String tabla) {
        validarTexto(tabla, "Tabla");
        return bitacorarepository.findByTablaAfectadaContainingIgnoreCase(tabla);
    }

    public List<Bitacora> obtenerPorAccion(String accion) {
        validarTexto(accion, "Acción");
        return bitacorarepository.findByAccionContainingIgnoreCase(accion);
    }

    public List<Bitacora> obtenerPorFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("Fecha inválida");
        }
        return bitacorarepository.findByFechaCambio(fecha);
    }



    //RESUMEN//
    public String resumenUsuario(Long idUsuario) {

        validarUsuario(idUsuario);

        List<Bitacora> registros = bitacorarepository.findByIdUsuario(idUsuario);

        if (registros.isEmpty()) {
            return "El usuario no tiene actividad registrada";
        }

        long creaciones = registros.stream().filter(r -> r.getAccion().equals("CREAR")).count();
        long actualizaciones = registros.stream().filter(r -> r.getAccion().equals("ACTUALIZAR")).count();
        long eliminaciones = registros.stream().filter(r -> r.getAccion().equals("ELIMINAR")).count();
        long login = registros.stream().filter(r -> r.getAccion().equals("LOGIN")).count();
        long actividadInusual = registros.stream().filter(r -> r.getAccion().equals("ACTIVIDAD INUSUAL")).count();
        long totalreportes = creaciones + actualizaciones + eliminaciones + login + actividadInusual;

        return "Usuario: " + idUsuario +
                " | Crear: " + creaciones +
                " | Actualizar: " + actualizaciones +
                " | Eliminar: " + eliminaciones + 
                " | Login: " + login + 
                " | actividad inusual: " + actividadInusual + 
                " | El usuario: " + idUsuario + " tiene un total de: " +  totalreportes  +  " reportes ";
    }
}
