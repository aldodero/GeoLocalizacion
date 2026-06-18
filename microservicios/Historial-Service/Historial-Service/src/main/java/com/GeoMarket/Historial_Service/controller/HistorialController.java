package com.GeoMarket.Historial_Service.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GeoMarket.Historial_Service.model.Historial;
import com.GeoMarket.Historial_Service.service.HistorialService;
import com.GeoMarket.Historial_Service.service.client.ProductoClient;

@RestController
@RequestMapping("/api/historial")
public class HistorialController {


    @Autowired
    private HistorialService historialservice;

    @Autowired
    private ProductoClient productoClient;



    //listar historial por usuario y por id de local
@GetMapping("/listar/{idUsuario}/{idLocal}")
public List<Map<String, Object>> listarPorLocal(

        @PathVariable Long idUsuario,

        @PathVariable Long idLocal
) {

    List<Historial> lista =
            historialservice
            .listarPorUsuarioYLocal(
                    idUsuario,
                    idLocal
            );

    return lista.stream().map(h -> {

        Map<String, Object> map =
                new HashMap<>();

        map.put(
                "tipoBusqueda",
                h.getTipoBusqueda()
        );

        map.put(
                "fecha",
                h.getFechaEscaneo()
        );

        map.put(
                "hora",
                h.getHoraEscaneo()
        );

        Object productoObj =
                productoClient.obtenerProducto(
                        h.getIdProducto()
                );
        System.out.println(
        "PRODUCTO HISTORIAL = " + productoObj);

        if (productoObj instanceof Map<?, ?> producto) {

            Object nombre =
                    producto.get(
                            "nombreProducto"
                    );

            Object codigo =
                    producto.get(
                            "codigoProducto"
                    );

            map.put(
                    "nombreProducto",
                    nombre != null
                            ? nombre.toString()
                            : "-"
            );

            map.put(
                    "codigoProducto",
                    codigo != null
                            ? codigo.toString()
                            : "-"
            );

            Object tipoObj =
                    producto.get(
                            "tipoProducto"
                    );

            if (tipoObj instanceof Map<?, ?> tipoProducto) {

                Object tipoNombre =
                        tipoProducto.get(
                                "nombreTipoProducto"
                        );

                map.put(
                        "tipo",
                        tipoNombre != null
                                ? tipoNombre.toString()
                                : "-"
                );

            } else {

                map.put("tipo", "-");
            }

        } else {

            map.put(
                    "nombreProducto",
                    "No encontrado"
            );

            map.put(
                    "codigoProducto",
                    "-"
            );

            map.put(
                    "tipo",
                    "-"
            );
        }

        return map;

    }).toList();
}





@GetMapping("/debug")
public List<Historial> debug() {
    return historialservice.obtenerTodos();
}




@GetMapping("/listar")
public List<Map<String, Object>> todos() {

    List<Historial> lista = historialservice.obtenerTodos();

    return lista.stream().map(h -> {

        Map<String, Object> map = new HashMap<>();

        map.put("tipoBusqueda", h.getTipoBusqueda());
        map.put("fecha", h.getFechaEscaneo());
        map.put("hora", h.getHoraEscaneo());

        
        Object productoObj =
        productoClient.obtenerProducto(h.getIdProducto());

System.out.println(productoObj);

if (productoObj instanceof Map<?, ?> producto) {

    Object nombre =
            producto.get("nombreProducto");

    Object codigo =
            producto.get("codigoProducto");

    map.put(
        "nombreProducto",
        nombre != null
                ? nombre.toString()
                : "-"
    );

    map.put(
        "codigoProducto",
        codigo != null
                ? codigo.toString()
                : "-"
    );

    Object tipoObj =
            producto.get("tipoProducto");

    if (tipoObj instanceof Map<?, ?> tipoProducto) {

        Object tipoNombre =
                tipoProducto.get("nombreTipoProducto");

        map.put(
            "tipo",
            tipoNombre != null
                    ? tipoNombre.toString()
                    : "-"
        );

    } else {

        map.put("tipo", "-");
    }

} else {

    map.put("nombreProducto", "No encontrado");
    map.put("codigoProducto", "-");
    map.put("tipo", "-");
}

        return map;

    }).toList();
}





//GUARDAR BUSQUEDA (ESCANEO, CODIGO, BUSQUEDA)
   @PostMapping("/guardar")
public Historial guardarEscaneo(@RequestBody Map<String, Object> data) {

    Long idUsuario = Long.valueOf(data.get("idUsuario").toString());
    Long idProducto = Long.valueOf(data.get("idProducto").toString());
    Long idLocal = Long.valueOf(data.get("idLocal").toString());
    String tipoBusqueda = data.get("tipoBusqueda").toString();

    return historialservice.guardarEscaneo(
        idUsuario,
        idProducto,
        idLocal,
        tipoBusqueda
    );
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

