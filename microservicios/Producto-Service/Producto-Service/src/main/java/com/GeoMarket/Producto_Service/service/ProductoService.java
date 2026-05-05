package com.GeoMarket.Producto_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GeoMarket.Producto_Service.model.Producto;
import com.GeoMarket.Producto_Service.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productorepository;

        //VALIDACIONES//
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

    private void validarProducto(Producto p) {
        if (p == null) {
            throw new IllegalArgumentException("Producto obligatorio");
        }

        validarTexto(p.getNombreProducto(), "Nombre");
        validarTexto(p.getCodigoProducto(), "Código");

        if (p.getPrecio() == null || p.getPrecio() < 0) {
            throw new IllegalArgumentException("Precio inválido");
        }

        if (p.getStock() < 0) {
            throw new IllegalArgumentException("Stock inválido");
        }

        if (p.getTipoProducto() == null) {
            throw new IllegalArgumentException("Tipo de producto obligatorio");
        }
    }







    //ADMIN CREAR PRODUCTO///
    public Producto crearProducto(Producto p) {

        validarProducto(p);

        if (productorepository.existsByCodigoProducto(p.getCodigoProducto())) {
            throw new RuntimeException("El código ya existe");
        }

        return productorepository.save(p);
    }

    public List<Producto> listarProductos() {
        return productorepository.findAll();
    }




    //ACTUALIZAR PRODUCTO POR ID
    public Producto actualizarProducto(Long id, Producto datos) {

        validarId(id);

        Producto existente = productorepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (datos.getNombreProducto() != null) {
            existente.setNombreProducto(datos.getNombreProducto());
        }

        if (datos.getCodigoProducto() != null &&
                !datos.getCodigoProducto().equals(existente.getCodigoProducto())) {

            if (productorepository.existsByCodigoProducto(datos.getCodigoProducto())) {
                throw new RuntimeException("El código ya existe");
            }

            existente.setCodigoProducto(datos.getCodigoProducto());
        }

        if (datos.getFechaElaboracion() != null) {
            existente.setFechaElaboracion(datos.getFechaElaboracion());
        }

        if (datos.getFechaVencimiento() != null) {
            existente.setFechaVencimiento(datos.getFechaVencimiento());
        }

        if (datos.getPrecio() != null) {
            if (datos.getPrecio() < 0) {
                throw new IllegalArgumentException("Precio inválido");
            }
            existente.setPrecio(datos.getPrecio());
        }

        if (datos.getStock() >= 0) {
            existente.setStock(datos.getStock());
        }

        if (datos.getTipoProducto() != null) {
            existente.setTipoProducto(datos.getTipoProducto());
        }

        return productorepository.save(existente);
    }





    //ELIMINAR PRODUCTO POR ID
    public void eliminarProducto(Long id) {

        validarId(id);

        if (!productorepository.existsById(id)) {
            throw new RuntimeException("Producto no existe");
        }
        productorepository.deleteById(id);
    }




    
    //TRABAJADOR ESCANEA PRODUCTO
    public Producto escanearProducto(String codigo) {

        validarTexto(codigo, "Código");

        return productorepository.findByCodigoProductoIgnoreCase(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }




    // BUSCAR POR CÓDIGO
    public Producto buscarPorCodigo(String codigo) {
        return escanearProducto(codigo);
    }



    // BUSCAR POR NOMBRE
    public List<Producto> buscarPorNombre(String nombre) {
        validarTexto(nombre, "Nombre");
        return productorepository.findByNombreProductoContainingIgnoreCase(nombre);
    }





    //FILTROS
    public List<Producto> buscarPorTipo(Long idTipo) {
        validarId(idTipo);
        return productorepository.findByTipoProducto_IdTipoProducto(idTipo);
    }


    //BUSCA PRODUCTOS SIN STOCK
    public List<Producto> productosSinStock() {
        return productorepository.findByStock(0);
    }   



    //BUSCAR PRODUCTO CON BAJO STOCK
    public List<Producto> productosBajoStock(int limite) {
        if (limite < 0) {
            throw new IllegalArgumentException("Límite inválido");
        }
        return productorepository.findByStockLessThan(limite);
    }


    //TRAE LISTA DE PRODUCTO POR RANGO DE PRECIO
    public List<Producto> productosPorRangoPrecio(Double min, Double max) {

        if (min == null || max == null || min < 0 || max < 0 || min > max) {
            throw new IllegalArgumentException("Rango inválido");
        }

        return productorepository.findByPrecioBetween(min, max);
    }





   //MAS VALIDACIONES 
    public Boolean existePorId(Long id) {
        if (id == null) return false;
        return productorepository.existsById(id);
    }



    public Boolean existePorCodigo(String codigo) {
        if (codigo == null) return false;
        return productorepository.existsByCodigoProducto(codigo);
    }

   



    //CONTEOS
    public Long contarProductos() {
        return productorepository.count();
    }

    public Long contarSinStock() {
        return productorepository.countByStock(0);
    }

    public Long contarPorTipo(Long idTipo) {
        return productorepository.countByTipoProducto_IdTipoProducto(idTipo);
    }


    //RESUMEN GENERAL DE PRODUCTOS
    public String resumenGeneral() {

        Long total = contarProductos();
        Long sinStock = contarSinStock();
        long totalinventario = total + sinStock;

        return "Productos → Total: " + total + 
        " | Sin stock: " + sinStock + 
        " | total inventario : "  +  totalinventario;
    }
}