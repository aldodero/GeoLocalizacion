package com.GeoMarket.Reporte_Service.repository;



import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GeoMarket.Reporte_Service.model.Bitacora;


@Repository
public interface  BitacoraRepository extends JpaRepository<Bitacora, Long>{


 List<Bitacora> findByIdUsuario(Long idUsuario);

    List<Bitacora> findByTablaAfectadaContainingIgnoreCase(String tablaAfectada);

    List<Bitacora> findByAccionContainingIgnoreCase(String accion);

    List<Bitacora> findByFechaCambio(LocalDate fechaCambio);

    

}
