package com.GeoMarket.Notificacion_Service.model;

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
@Table(name="tiponotificacion")
public class TipoNotificacion {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idTipoNotificacion;
    @Column(nullable= false)
    private String nombreNotificacion;
    @Column(nullable= false)
    private String estadoNotificacion;
}
