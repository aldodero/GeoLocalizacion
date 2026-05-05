package com.GeoMarket.Producto_Service.model;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="producto")
public class Producto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idProducto;

    @Column(nullable=false, unique = true)
    private String codigoProducto;

    @Column(nullable=false)
    private String nombreProducto;

    @Column(name = "fecha_elaboracion", nullable=false)
    private LocalDate fechaElaboracion;

    @Column(name = "fecha_vencimiento", nullable=false)
    private LocalDate fechaVencimiento;

    @Column(nullable=false)
    private Double precio;
    @Column(nullable=false)
    private int stock;




    //relacion muchos a uno con tipo producto
    @ManyToOne
    @JoinColumn(name= "tipo_id")
    private TipoProducto tipoProducto;


    //relacion de muchos a uno con ubicacion producto
    @ManyToOne
    @JoinColumn(name= "ubicacion_id")
    private UbicacionProducto ubicacionProducto;
    
}