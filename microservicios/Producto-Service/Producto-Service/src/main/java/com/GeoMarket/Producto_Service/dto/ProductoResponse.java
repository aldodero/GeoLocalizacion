package com.GeoMarket.Producto_Service.dto;

public class ProductoResponse {

    private Long idProducto;
    private String nombreProducto;
    private String codigoProducto;
    private String tipo;
    private Double precio;
    private String marca;
    private String categoria;

    public ProductoResponse(
            Long idProducto,
            String nombreProducto,
            String codigoProducto,
            String tipo,
            String marca,
            String categoria,
            Double precio
    ) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.codigoProducto = codigoProducto;
        this.tipo = tipo;
        this.marca = marca;
        this.categoria = categoria;
        this.precio = precio;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMarca(){
        return marca;
    }

    public String getCategoria(){
        return categoria;
    }

    public Double getPrecio() {
        return precio;
    }
}