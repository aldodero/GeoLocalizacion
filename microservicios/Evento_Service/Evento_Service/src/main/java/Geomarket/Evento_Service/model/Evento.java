package Geomarket.Evento_Service.model;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Evento")
public class Evento {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idEvento;
    
    @Column(nullable=false)
    private String titulo;
    @Column(nullable=false)
    private String descripcion;
    @Column(name="fecha_evento", nullable=false)
    private LocalDate fechaEvento;
    @Column(name="hora_evento", nullable=false)
    private LocalDateTime horaEvento;
    
    @Column(nullable = false)
    private Boolean notificacionEnviada = false;



    //id externos
    @Column(nullable=false)
    private Long idUsuario;
    @Column(nullable=false)
    private Long idLocal;
    
    
    
    //relacion uno a muchos con tipoevento dentro del mismo microservicio
    @ManyToOne
    @JoinColumn(name="tipo_id")
    private TipoEvento tipoEvento;
}
