package com.GeoMarket.Historial_Service.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GeoMarket.Historial_Service.model.Historial;
import com.GeoMarket.Historial_Service.service.HistorialService;

@RestController
@RequestMapping("/api/Historial-Escaneo")
public class HistorialController {


    @Autowired
    private HistorialService historialservice;

   

    //GUARDAR ESCANEO
    @PostMapping("/guardar")
    public Historial guardarEscaneo(@RequestParam Long idUsuario, @RequestParam Long idProducto,@RequestParam String tipoBusqueda) {
        return historialservice.guardarEscaneo(idUsuario, idProducto, tipoBusqueda);
    }

    //TRABAJADOR////
    //OBTENER HISTORIAL POR USUARIO CON ID
    @GetMapping("/usuario/{idUsuario}")
    public List<Historial> obtenerHistorialUsuario(@PathVariable Long idUsuario) {
        return historialservice.obtenerHistorialPropio(idUsuario);
    }

    //OBTENER HISTORIAL POR FECHA
    @GetMapping("/usuario/{idUsuario}/fecha")
    public List<Historial> obtenerPorFecha(@PathVariable Long idUsuario,@RequestParam String fecha) {
        return historialservice.obtenerHistorialPorFecha(idUsuario,LocalDate.parse(fecha)
        );
    }

    //OBTENER ULTIMOS ESCANEOS POR USUARIO
    @GetMapping("/usuario/{idUsuario}/ultimos")
    public List<Historial> obtenerUltimosEscaneos(@PathVariable Long idUsuario,@RequestParam int limite) {
        return historialservice.obtenerUltimosEscaneos(idUsuario, limite);
    }


    //OBTENER CONTEO DE ESCANEOS DEL DIA
    @GetMapping("/usuario/{idUsuario}/hoy")
    public Long contarEscaneosHoy(@PathVariable Long idUsuario) {
        return historialservice.contarEscaneosHoy(idUsuario);
    }


    //OBTENER PRODUCTOS MAS ESCANEADOS POR USUARIO
    @GetMapping("/usuario/{idUsuario}/top-productos")
public List<Map<String, Object>> productosMasEscaneadosUsuario(@PathVariable Long idUsuario) {
    return historialservice.obtenerProductosMasEscaneadosPorUsuario(idUsuario);
}
    


//ADMIN/////
    //OBTENER LISTADO
    @GetMapping("/todos")
    public List<Historial> obtenerTodos() {
        return historialservice.obtenerTodos();
    }

    //OBTENER ESCANEOS POR RANGO DE FECHA
    @GetMapping("/rango-fecha")
    public List<Historial> obtenerPorRangoFechas(@RequestParam String inicio,@RequestParam String fin) {
        return historialservice.obtenerPorRangoFechas(LocalDate.parse(inicio),LocalDate.parse(fin)
        );
    }

   

    //CONTROL TRABAJADORES///
    @GetMapping("/usuario/{idUsuario}/conteo")
    public Long contarPorUsuario(@PathVariable Long idUsuario) {
        return historialservice.contarEscaneosPorUsuario(idUsuario);
    }


    //RANKING ESANEOS POR TRABAJADOR
    @GetMapping("/ranking")
    public List<Object[]> rankingTrabajadores() {
        return historialservice.rankingTrabajadores();
    }

    

    //CONTROL PRODUCTOS//
    //OBTENER HISTORIAL POR PRODUCTO
    @GetMapping("/producto/{idProducto}")
    public List<Historial> obtenerPorProducto(@PathVariable Long idProducto) {
        return historialservice.obtenerHistorialPorProducto(idProducto);
    }

    //OBTNER CONTEO POR PRODUCTO 
    @GetMapping("/producto/{idProducto}/conteo")
    public Long contarPorProducto(@PathVariable Long idProducto) {
        return historialservice.contarEscaneosPorProducto(idProducto);
    }

    //OBTENER PRODUCTOS MAS ESCANEADOS
    @GetMapping("/productos/top")
    public List<Object[]> productosMasEscaneados() {
        return historialservice.productosMasEscaneados();
    }

   
    //FILTROS///
    //FILTRAR POR BUSQUEDA
    @GetMapping("/tipo")
    public List<Historial> filtrarPorTipo(@RequestParam String tipoBusqueda) {
        return historialservice.filtrarPorTipoBusqueda(tipoBusqueda);
    }


    //DETECTAR ACTIVIDAES INUSUALES
    @GetMapping("/inusual")
    public List<Object[]> actividadesInusuales() {
        return historialservice.detectarActividadesInusuales();
    }   

    
    //OBTENER HISTORIAL DETALLADO
    @GetMapping("/{id}")
    public Historial obtenerDetalle(@PathVariable Long id) {
        return historialservice.obtenerHistorialDetallado(id);
    }

   

    //GESTION//
    //ELIMINAR REGISTRO DE HISTORIAL
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        historialservice.eliminarRegistro(id);
        return "Historial eliminado correctamente";
    }

    //LIMPIAR HISTORIALES ANTIGUOS
    @DeleteMapping("/limpiar")
    public String limpiar(@RequestParam String fecha) {
        historialservice.limpiarHistorialAntiguo(LocalDate.parse(fecha));
        return "Historial antiguo eliminado";
    }
}

