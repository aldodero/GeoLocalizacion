package com.GeoMarket.Producto_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GeoMarket.Producto_Service.model.Producto;
import com.GeoMarket.Producto_Service.model.UbicacionProducto;
import com.GeoMarket.Producto_Service.repository.ProductoRepository;
import com.GeoMarket.Producto_Service.repository.UbicacionProductoRepository;

@Service
public class UbicacionProductoService {

    @Autowired
    private UbicacionProductoRepository ubicacionRepository;

    @Autowired
    private ProductoRepository productoRepository;

  



    //VALIDAR ID
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
    }

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(campo + " inválido");
        }
    }

    private void validarUbicacion(UbicacionProducto u) {
        if (u == null) {
            throw new IllegalArgumentException("Ubicación obligatoria");
        }

        validarTexto(u.getPasillo(), "Pasillo");
        validarTexto(u.getSeccion(), "Sección");
        validarTexto(u.getGondola(), "Góndola");
    }

  

    //CREAR UBI DE PRODUCTO
    public UbicacionProducto crear(UbicacionProducto u) {

        validarUbicacion(u);

        return ubicacionRepository.save(u);
    }

    public List<UbicacionProducto> listar() {
        return ubicacionRepository.findAll();
    }



    //OBTENER UBI POR ID 
    public UbicacionProducto obtenerPorId(Long id) {

        validarId(id);

        return ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));
    }




    //ACTUALIZAR UBI POR ID 
    public UbicacionProducto actualizar(Long id, UbicacionProducto datos) {

        validarId(id);

        UbicacionProducto existente = obtenerPorId(id);

        if (datos.getPasillo() != null) {
            existente.setPasillo(datos.getPasillo());
        }

        if (datos.getSeccion() != null) {
            existente.setSeccion(datos.getSeccion());
        }

        if (datos.getGondola() != null) {
            existente.setGondola(datos.getGondola());
        }

        return ubicacionRepository.save(existente);
    }



    //ELIMIAR UBI POR ID
    public void eliminar(Long id) {

        validarId(id);

        if (!ubicacionRepository.existsById(id)) {
            throw new RuntimeException("Ubicación no existe");
        }

        ubicacionRepository.deleteById(id);
    }

   




    // BUSCAR UBICACIÓN DESDE PRODUCTO (ESCANEO)
    public UbicacionProducto obtenerUbicacionPorCodigo(String codigoProducto) {

        validarTexto(codigoProducto, "Código");

        Producto producto = productoRepository.findByCodigoProductoIgnoreCase(codigoProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getUbicacionProducto() == null) {
            throw new RuntimeException("El producto no tiene ubicación asignada");
        }

        return producto.getUbicacionProducto();
    }





    // EXTO PARA APP
    public String obtenerUbicacionTexto(String codigoProducto) {

        UbicacionProducto u = obtenerUbicacionPorCodigo(codigoProducto);

        return "Pasillo: " + u.getPasillo()
                + " | Sección: " + u.getSeccion()
                + " | Góndola: " + u.getGondola();
    }




    //FORMATO MAPA
    public String ubicacionParaMapa(String codigoProducto) {

        UbicacionProducto u = obtenerUbicacionPorCodigo(codigoProducto);

        return "Dirígete al pasillo " + u.getPasillo()
                + ", sección " + u.getSeccion()
                + ", góndola " + u.getGondola();
    }

   



    //FILTROS///
    public List<UbicacionProducto> buscarPorPasillo(String pasillo) {
        validarTexto(pasillo, "Pasillo");
        return ubicacionRepository.findByPasilloIgnoreCase(pasillo);
    }

    public List<UbicacionProducto> buscarPorSeccion(String seccion) {
        validarTexto(seccion, "Sección");
        return ubicacionRepository.findBySeccionIgnoreCase(seccion);
    }

    public List<UbicacionProducto> buscarPorGondola(String gondola) {
        validarTexto(gondola, "Góndola");
        return ubicacionRepository.findByGondolaIgnoreCase(gondola);
    }






    //VALIDACION///
    public Boolean existePorId(Long id) {
        if (id == null) return false;
        return ubicacionRepository.existsById(id);
    }

    


    //CONTEO
    public Long contarUbicaciones() {
        return ubicacionRepository.count();
    }


    //RESUMEN GENERAL
    public String resumen() {
        Long total = contarUbicaciones();
        return "Ubicaciones → Total: " + total;
    }
}