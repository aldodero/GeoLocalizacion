package com.GeoMarket.Usuario_Service.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable= false, unique = true)
    private String rutUsuario;
    
    @Column(nullable=false)
    private String primerNombre;

    @Column(nullable=false)
    private String segundoNombre;

    @Column(nullable=false)
    private String primerApellido;

    @Column(nullable=false)
    private String segundoApellido;

    @Column(nullable=false)
    private int telefono;

    @Column(nullable=false)
    private String correo;

    @Column(nullable=false)
    private String contrasena;

    @Column(nullable=false)
    private Date fechaRegistro;

    @Column(nullable=false)
    private String direccion;

    @Column(nullable=false)
    private Date fechaNacimiento;

    @Column(nullable=false)
    private String genero;

    @Column(nullable=false)
    private boolean activo;
    


    //RELACION CON TIPO DE USUSARIO
    @JsonBackReference
    @ManyToOne    
    @JoinColumn(name= "tipo_usuario_id", nullable=false)
    private TipoUsuario tipoUsuario;



}
