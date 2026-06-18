package Geomarket.ProductoLocal_Service.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Geomarket.ProductoLocal_Service.dto.EscaneoResponse;
import Geomarket.ProductoLocal_Service.model.UbicacionProducto;
import Geomarket.ProductoLocal_Service.service.UbicacionProductoService;

@RestController
@RequestMapping("/api/ubicaciones")
@CrossOrigin("*")
public class UbicacionProductoController {

    @Autowired
    private UbicacionProductoService ubicacionProductoService;

    // CREAR
    @PostMapping("/guardar")
    public UbicacionProducto guardar(@RequestBody UbicacionProducto ubicacion) {
        return ubicacionProductoService.crear(ubicacion);
    }

    // LISTAR
    @GetMapping("/listar")
    public List<UbicacionProducto> listar() {
        return ubicacionProductoService.listar();
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public UbicacionProducto obtenerPorId(@PathVariable Long id) {
        return ubicacionProductoService.obtenerPorId(id);
    }

    // ACTUALIZAR
    @PutMapping("/actualizar/{id}")
    public UbicacionProducto actualizar(@PathVariable Long id,@RequestBody UbicacionProducto datos) {
        return ubicacionProductoService.actualizar(id, datos);
    }


    // ELIMINAR
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id){
        ubicacionProductoService.eliminar(id);
        return "Ubicación eliminada correctamente";
    }



    // BUSCAR UBICACION POR CODIGO
    @GetMapping("/codigo")
    public UbicacionProducto obtenerPorCodigo( @RequestParam String codigo) {
        return ubicacionProductoService.obtenerUbicacionPorCodigo(codigo);
    }

    // TEXTO APP
    @GetMapping("/texto")
    public String obtenerTexto(@RequestParam String codigo) {
        return ubicacionProductoService.obtenerUbicacionTexto(codigo);
    }



    // TEXTO MAPA
    @GetMapping("/mapa")
    public String obtenerMapa(@RequestParam String codigo) {
        return ubicacionProductoService.ubicacionParaMapa(codigo);
    }

    // FILTRO PASILLO
    @GetMapping("/pasillo")
    public List<UbicacionProducto> buscarPasillo(@RequestParam String pasillo) {
        return ubicacionProductoService.buscarPorPasillo(pasillo);
    }

    // FILTRO SECCION
    @GetMapping("/seccion")
    public List<UbicacionProducto> buscarSeccion(@RequestParam String seccion) {
        return ubicacionProductoService.buscarPorSeccion(seccion);
    }

    // FILTRO GONDOLA
    @GetMapping("/gondola")
    public List<UbicacionProducto> buscarGondola(@RequestParam String gondola) {
        return ubicacionProductoService.buscarPorGondola(gondola);
    }

    // RESUMEN
    @GetMapping("/resumen")
    public String resumen() {
        return ubicacionProductoService.resumen();
    }
    
    
    @GetMapping("/escanear")public EscaneoResponse escanear(@RequestParam String codigo, @RequestParam Long idLocal) {
        return ubicacionProductoService.obtenerProductoEscaneado(codigo,idLocal);
    }
}