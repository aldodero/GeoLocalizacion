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


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ciudad")
public class Ciudad {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idCiudad;
    @Column(nullable=false)
    private String nombreCiudad;
    

    @ManyToOne
    @JoinColumn(name="id_region")
    private Region region;
}
