package com.GeoMarket.Reporte_Service.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="bitacora")
public class Bitacora {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idRegistro;

    @Column(nullable=false)
    private String accion;
    @Column(nullable=false)
    private String tablaAfectada;
    @Column(nullable=false)
    private LocalDate fechaCambio;
    @Column(nullable=false)
    private LocalDateTime horaCambio;


    @Column(nullable=false)
    private Long idUsuario;   
}
