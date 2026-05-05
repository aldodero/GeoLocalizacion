package com.GeoMarket.Notificacion_Service.model;

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

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="notificacion")
public class Notificacion {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idNotificacion;

    @Column(nullable=false)
    private String mensaje;
    @Column(nullable=false)
    private LocalDate fechaNotificacion;
    @Column(nullable=false)
    private LocalDateTime horaNotificacion;
    @Column(nullable= false)
    private String estadoNotificacion;


    @Column(nullable=false)
    private Long idUsuario;


    //relacion con tipo notificacion
    @ManyToOne
    @JoinColumn(name="id_tipo_notificacion")
    private TipoNotificacion tipoNotificacion;

    
}
