package Geomarket.ProductoLocal_Service.model;

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
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ProductoLocal")
public class ProductoLocal {
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idProductoLocal;
    @Column(nullable=false)
    private LocalDateTime fechaActualizacion;
    @Column(nullable= false)
    private Double stock;
    @Column(nullable=false) 
    private String estado;
    
    //entidades extenras
    @Column(nullable=false) 
    private Long idProducto;
    @Column(nullable=false) 
    private Long idLocal;
    
}

   
