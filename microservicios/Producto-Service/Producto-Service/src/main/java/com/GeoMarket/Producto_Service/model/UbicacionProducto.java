package com.GeoMarket.Producto_Service.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ubicacion_producto")
public class UbicacionProducto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idUbicacion;
    @Column(nullable= false)
    private String pasillo;
    @Column(nullable= false)
    private String seccion;
    @Column(nullable= false)
    private String gondola;
    

    //relacion de uno a muchos con producto
    @OneToMany(mappedBy="ubicacionProducto")
    private List<Producto> productos;
}
