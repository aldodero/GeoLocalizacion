package com.GeoMarket.Reporte_Service.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name="reporte")
public class Reporte {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idReporte;
     @Column(nullable=false)
    private String nombreReporte;
     @Column(nullable=false)
    private String descripcion;
    private LocalDate fechaReporte;
     @Column(nullable=false)
    private LocalDateTime horaReporte;
     @Column(nullable=false)
    private String estadoReporte;
     @Column(nullable=false)
    private String prioridad;


     @Column(nullable=false)
    private Long idUsuario;

    @ManyToOne
    @JoinColumn(name="id_tipo_reporte")
    private TipoReporte tipoReporte;



}
