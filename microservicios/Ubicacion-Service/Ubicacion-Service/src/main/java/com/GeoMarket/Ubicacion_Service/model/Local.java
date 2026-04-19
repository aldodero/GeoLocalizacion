package com.GeoMarket.Ubicacion_Service.model;

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
@Table(name="local")
public class Local {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idLocal;

    @Column(nullable= false, unique= true)
    private String codigoLocal;
    @Column(nullable= false)
    private String nombreLocal;
    @Column(nullable= false)
    private String direccionLocal;
    

    
    @Column(nullable= false)
    private Double latitud;

    @Column(nullable= false)
    private Double longitud;

    @ManyToOne
    @JoinColumn(name="id_comuna")
    private Comuna comuna;
}
