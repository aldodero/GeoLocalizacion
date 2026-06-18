package Geomarket.ProductoLocal_Service.model;

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


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ubicacion_producto")
public class UbicacionProducto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idUbicacion;
    @Column(nullable= false)
    private String pasillo;
    @Column(nullable= false)
    private String seccion;
    @Column(nullable= false)
    private String gondola;
    

    //relacion de muchos a uno con productoLocal
    @ManyToOne
    @JoinColumn(name="producto_local_id")
    private ProductoLocal productolocal;
}
