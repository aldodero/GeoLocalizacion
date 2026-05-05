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
@Table(name="comuna")
public class Comuna {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idComuna;

    @Column(nullable=false)
    private String nombreComuna;


    @ManyToOne
    @JoinColumn(name="id_ciudad")
    private Ciudad ciudad;
}
