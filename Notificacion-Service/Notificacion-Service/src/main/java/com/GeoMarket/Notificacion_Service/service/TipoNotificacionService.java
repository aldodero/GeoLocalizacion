package com.GeoMarket.Notificacion_Service.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.GeoMarket.Notificacion_Service.model.TipoNotificacion;
import com.GeoMarket.Notificacion_Service.repository.TipoNotificacionRepository;
import java.util.List;




@Service
public class TipoNotificacionService {

    @Autowired
    private TipoNotificacionRepository tiponotificacionrepository;



    //VALIDACIONES
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre inválido");
        }
    }



    //ADMIN//
    // CREAR TIPO 
    public TipoNotificacion crearTipo(TipoNotificacion tipo) {

        if (tipo == null) {
            throw new IllegalArgumentException("Tipo obligatorio");
        }

        validarNombre(tipo.getNombreNotificacion());

        return tiponotificacionrepository.save(tipo);
    }



    // LISTAR TODOS LOS TIPOS 
    public List<TipoNotificacion> listar() {
        return tiponotificacionrepository.findAll();
    }



    // OBTENER TIPO POR ID
    public TipoNotificacion obtenerPorId(Long id) {
        validarId(id);
        return tiponotificacionrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));
    }



    // ACTUALIZAR TIPO POR ID
    public TipoNotificacion actualizar(Long id, TipoNotificacion datos) {
        validarId(id);
        TipoNotificacion existente = obtenerPorId(id);

        if (datos.getNombreNotificacion() != null) {
            validarNombre(datos.getNombreNotificacion());
            existente.setNombreNotificacion(datos.getNombreNotificacion());
        }

        if (datos.getEstadoNotificacion() != null) {
            existente.setEstadoNotificacion(datos.getEstadoNotificacion());
        }
        return tiponotificacionrepository.save(existente);
    }



    // ELIMINAR TIPO
    public void eliminar(Long id) {
        validarId(id);
        if (!tiponotificacionrepository.existsById(id)) {
            throw new RuntimeException("Tipo no existe");
        }
        tiponotificacionrepository.deleteById(id);
    }




    // BUSCAR POR NOMBRE
    public List<TipoNotificacion> buscarPorNombre(String nombre) {
        validarNombre(nombre);
        return tiponotificacionrepository.findByNombreNotificacionContainingIgnoreCase(nombre);
    }




}
