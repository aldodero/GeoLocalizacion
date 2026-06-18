package Geomarket.Evento_Service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tipo_evento")
public class TipoEvento {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idTipoEvento;
    @Column(nullable=false)
    private String nombreTipoEvento;
    @Column(nullable=false)
    private String descripcion;



    
    //relacion uno a muchos con evento json ignore para que a la hora de ejecutar get traiga uno
    @JsonIgnore
    @OneToMany(mappedBy = "tipoEvento")
    private List<Evento> eventos;

}



