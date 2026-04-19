package com.GeoMarket.Historial_Service.model;

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


        
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="historial")
public class Historial {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idHistorial;

    @Column(nullable=false)
    private String tipoBusqueda;
    @Column(nullable=false)
    private LocalDate fechaEscaneo;
     @Column(nullable=false)
    private LocalDateTime horaEscaneo;


    //para mas a futuro usarlo para conectar con user and product
    private Long idUsuario;
    private Long idProducto;
}
