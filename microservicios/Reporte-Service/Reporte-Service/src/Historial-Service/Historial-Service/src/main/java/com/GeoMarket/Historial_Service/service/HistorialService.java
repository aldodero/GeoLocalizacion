package com.GeoMarket.Historial_Service.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.GeoMarket.Historial_Service.model.Historial;
import com.GeoMarket.Historial_Service.repository.HistorialRepository;


@Service
public class HistorialService {


    @Autowired
    private HistorialRepository historialrepository;
    
 

   //VALIDACIONES///
    private void validarIdUsuario(Long idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            throw new IllegalArgumentException("idUsuario inválido");
        }
    }

    private void validarIdProducto(Long idProducto) {
        if (idProducto == null || idProducto <= 0) {
            throw new IllegalArgumentException("idProducto inválido");
        }
    }

    private void validarFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("Fecha obligatoria");
        }
    }



    //GUARDAR ESCANEO//
    public Historial guardarEscaneo(Long idUsuario, Long idProducto, String tipoBusqueda) {

        validarIdUsuario(idUsuario);
        validarIdProducto(idProducto);

        if (tipoBusqueda == null || tipoBusqueda.isBlank()) {
            throw new IllegalArgumentException("tipoBusqueda obligatorio");
        }


        Historial h = new Historial();
        h.setIdUsuario(idUsuario);
        h.setIdProducto(idProducto);
        h.setTipoBusqueda(tipoBusqueda);
        h.setFechaEscaneo(LocalDate.now());
        h.setHoraEscaneo(LocalDateTime.now());

        return historialrepository.save(h);
    }

  


    //TRABAJADOR OBTENGA SU HISTORIAL PROPIO
    public List<Historial> obtenerHistorialPropio(Long idUsuario) {
        validarIdUsuario(idUsuario);
        return historialrepository.findByIdUsuario(idUsuario);
    }


    //TRABAJADOR OBTENGA SU HISTORIAL POR FECHA
    public List<Historial> obtenerHistorialPorFecha(Long idUsuario, LocalDate fecha) {
        validarIdUsuario(idUsuario);
        validarFecha(fecha);
        return historialrepository.findByIdUsuarioAndFechaEscaneo(idUsuario, fecha);
    }

    
    //TRABAJADOR OBTENGA SUS ULSTIMOS ESCANEOS
    public List<Historial> obtenerUltimosEscaneos(Long idUsuario, int limite) {

    validarIdUsuario(idUsuario);

    if (limite <= 0) {
        throw new IllegalArgumentException("Límite inválido");
    }

    return historialrepository.findByIdUsuarioOrderByFechaEscaneoDescHoraEscaneoDesc(
                idUsuario,
                PageRequest.of(0, limite)
            )
            .getContent();
}


    //TRABAJADOR OBTENGA SU CUENTA DE ESCANEADOS DE HOY
    public Long contarEscaneosHoy(Long idUsuario) {
        validarIdUsuario(idUsuario);
        return historialrepository.countByIdUsuarioAndFechaEscaneo(idUsuario, LocalDate.now());
    }

    //TRABAJOR OBTENGA SU PRODUCTO MAS ESCANEADO POR EL
    public List<Map<String, Object>> obtenerProductosMasEscaneadosPorUsuario(Long idUsuario) {
    List<Object[]> resultados = historialrepository.productosMasEscaneadosPorUsuario(idUsuario);
    return resultados.stream().map(r -> {
        Map<String, Object> map = new HashMap<>();
        map.put("idProducto", r[0]);
        map.put("cantidad", r[1]);
        return map;
    }).toList();
}
    

    //ADMIN PUEDA LISTAR TODO 
    public List<Historial> obtenerTodos() {
        return historialrepository.findAll();
    }

    //ADMIN PUEDA OBTENER ULTIMOS REGISTROS DE USUARIO
    public List<Historial> obtenerUltimosRegistrosPorUsuario(Long idUsuario) {
        validarIdUsuario(idUsuario);
        return historialrepository.findTop10ByIdUsuarioOrderByFechaEscaneoDescHoraEscaneoDesc(idUsuario);
    }


    //ADMIN PUEDA VER HISTORIAL POR FECHA
    public List<Historial> obtenerPorRangoFechas(LocalDate inicio, LocalDate fin) {

        if (inicio == null || fin == null) {
            throw new IllegalArgumentException("Fechas obligatorias");
        }

        if (inicio.isAfter(fin)) {
            throw new IllegalArgumentException("Rango de fechas inválido");
        }

        return historialrepository.findByFechaEscaneoBetween(inicio, fin);
    }

    // ==============================
    // 👥 CONTROL TRABAJADORES
    // ==============================

    public List<Historial> obtenerHistorialPorUsuario(Long idUsuario) {
        validarIdUsuario(idUsuario);
        return historialrepository.findByIdUsuario(idUsuario);
    }

    public Long contarEscaneosPorUsuario(Long idUsuario) {
        validarIdUsuario(idUsuario);
        return historialrepository.countByIdUsuario(idUsuario);
    }

    public List<Object[]> rankingTrabajadores() {
        return historialrepository.rankingTrabajadores();
    }

    // ==============================
    // 📦 CONTROL PRODUCTOS
    // ==============================

    public List<Historial> obtenerHistorialPorProducto(Long idProducto) {
        validarIdProducto(idProducto);
        return historialrepository.findByIdProducto(idProducto);
    }

    public Long contarEscaneosPorProducto(Long idProducto) {
        validarIdProducto(idProducto);
        return historialrepository.countByIdProducto(idProducto);
    }

    public List<Object[]> productosMasEscaneados() {
        return historialrepository.productosMasEscaneados();
    }

    // ==============================
    // 🚨 FILTROS / AUDITORÍA
    // ==============================

    public List<Historial> filtrarPorTipoBusqueda(String tipoBusqueda) {
        if (tipoBusqueda == null || tipoBusqueda.isBlank()) {
            throw new IllegalArgumentException("tipoBusqueda inválido");
        }
        return historialrepository.findByTipoBusqueda(tipoBusqueda);
    }

    public List<Object[]> detectarActividadesInusuales() {
    return historialrepository.actividadInusual(LocalDate.now());
}

    public Historial obtenerHistorialDetallado(Long idHistorial) {
        return historialrepository.findById(idHistorial)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));
    }

    // ==============================
    // ⚙️ GESTIÓN
    // ==============================

    public void eliminarRegistro(Long idHistorial) {

        if (idHistorial == null || idHistorial <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        if (!historialrepository.existsById(idHistorial)) {
            throw new RuntimeException("El historial no existe");
        }

        historialrepository.deleteById(idHistorial);
    }



    public void limpiarHistorialAntiguo(LocalDate fechaLimite) {

        if (fechaLimite == null) {
            throw new IllegalArgumentException("Fecha límite obligatoria");
        }

        historialrepository.deleteByFechaEscaneoBefore(fechaLimite);
    }






}


