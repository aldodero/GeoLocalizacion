package com.GeoMarket.Reporte_Service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="tipo_reporte")
public class TipoReporte {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idTipoReporte;
    @Column(nullable=false)
    private String nombreTipoReporte;

    @Column(nullable= false)
    private String estado;
    
}
