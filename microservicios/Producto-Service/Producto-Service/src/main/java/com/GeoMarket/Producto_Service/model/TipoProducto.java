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


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name="tipo_producto")
public class TipoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoProducto;
    
    @Column(nullable=false )
    private String nombreTipoProducto;


    
    public void setIdTipoProducto(Long idTipoProducto) {
    this.idTipoProducto = idTipoProducto;
    }

    //relacion uno a muchos con tiporpdocuto
    @OneToMany(mappedBy = "tipoProducto")
    private List<Producto> productos;
}


