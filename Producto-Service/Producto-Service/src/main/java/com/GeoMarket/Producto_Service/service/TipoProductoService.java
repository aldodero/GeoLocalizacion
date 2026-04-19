package com.GeoMarket.Producto_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GeoMarket.Producto_Service.model.TipoProducto;
import com.GeoMarket.Producto_Service.repository.TipoProductoRepository;

@Service
public class TipoProductoService {

    @Autowired
    private TipoProductoRepository repository;

   

    //validaciones//
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
    }

    //validar nombre tipo producto
    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre inválido");
        }
    }



    //CREAR TIPO PRODUCTO
    public TipoProducto crearTipo(TipoProducto tipo) {

        if (tipo == null) {
            throw new IllegalArgumentException("Tipo obligatorio");
        }
        validarNombre(tipo.getNombreTipoProducto());

        if (repository.existsByNombreTipoProducto(tipo.getNombreTipoProducto())) {
            throw new RuntimeException("El tipo ya existe");
        }
        return repository.save(tipo);
    }





    //LISTAR TODOS LOS TIPOS DE PRODUCTOS
    public List<TipoProducto> listarTipos() {
        return repository.findAll();
    }



    //OBTENER TIPOS POR ID
    public TipoProducto obtenerPorId(Long id) {

        validarId(id);

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));
    }
  



    //ACTUALIZAR TIPO POR ID
    public TipoProducto actualizarTipo(Long id, TipoProducto datos) {

        validarId(id);

        TipoProducto existente = obtenerPorId(id);

        if (datos.getNombreTipoProducto() != null &&
                !datos.getNombreTipoProducto().equalsIgnoreCase(existente.getNombreTipoProducto())) {

            validarNombre(datos.getNombreTipoProducto());

            if (repository.existsByNombreTipoProducto(datos.getNombreTipoProducto())) {
                throw new RuntimeException("El nombre ya existe");
            }

            existente.setNombreTipoProducto(datos.getNombreTipoProducto());
        }

        return repository.save(existente);
    }





    //ELIMINAR TIPO PRODUCTO POR ID
    public void eliminarTipo(Long id) {

        validarId(id);

        if (!repository.existsById(id)) {
            throw new RuntimeException("Tipo no existe");
        }

        repository.deleteById(id);
    }

    

    //BUSCAR TIPO PRODUCTO POR NOMBRE
    public List<TipoProducto> buscarPorNombre(String nombre) {
        validarNombre(nombre);
        return repository.findByNombreTipoProductoContainingIgnoreCase(nombre);
    }


    //VALIDAR SI EXISTE POR.....
    public Boolean existePorId(Long id) {
        if (id == null) return false;
        return repository.existsById(id);
    }

    public Boolean existePorNombre(String nombre) {
        if (nombre == null) return false;
        return repository.existsByNombreTipoProducto(nombre);
    }

    

    //CONTADOR DE TIPOS
    public Long contarTipos() {
        return repository.count();
    }



    //RESUMEN GENERAL DE TIPOS DE PRODUCTOS
    public String resumen() {
        Long total = contarTipos();
        return "Tipos de producto → Total: " + total;
    }
}