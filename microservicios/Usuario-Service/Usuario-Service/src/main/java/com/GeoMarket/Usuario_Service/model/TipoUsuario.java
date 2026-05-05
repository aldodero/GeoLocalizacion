package com.GeoMarket.Usuario_Service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tipousuario")
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idTipoUsuario; 

    
    @Column(nullable= false, unique = true)
    private String NombreTipoUsuario;
    @Column(nullable=false)
    private String rolUsuario;
    @Column(nullable=false)
    private Boolean estado;
    @Column(nullable=false)
    private String descripcion;
    




    //RELACION CON USUARIO INVERSA
    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "tipoUsuario")
    private List<Usuario> usuarios;
}
