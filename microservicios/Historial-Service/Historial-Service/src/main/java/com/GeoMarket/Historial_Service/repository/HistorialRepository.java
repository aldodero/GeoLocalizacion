package com.GeoMarket.Historial_Service.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.GeoMarket.Historial_Service.model.Historial;

public interface HistorialRepository extends  JpaRepository<Historial, Long> {



List<Historial> findByIdUsuario(Long idUsuario);

List<Historial> findByIdProducto(Long idProducto);

List<Historial> findByIdUsuarioAndFechaEscaneo(Long idUsuario, LocalDate fecha);

Long countByIdUsuario(Long idUsuario);

Long countByIdProducto(Long idProducto);

Long countByIdUsuarioAndFechaEscaneo(Long idUsuario, LocalDate fecha);

List<Historial> findByFechaEscaneoBetween(LocalDate inicio, LocalDate fin);

List<Historial> findByTipoBusqueda(String tipoBusqueda);

List<Historial> findTop10ByIdUsuarioOrderByFechaEscaneoDescHoraEscaneoDesc(Long idUsuario);

void deleteByFechaEscaneoBefore(LocalDate fecha);


@Query("""
SELECT h.idUsuario, COUNT(h) 
FROM Historial h 
WHERE h.fechaEscaneo = :fecha 
GROUP BY h.idUsuario 
HAVING COUNT(h) > 50
""")
List<Object[]> actividadInusual(@Param("fecha") LocalDate fecha);



@Query("""
SELECT h.idProducto, COUNT(h) 
FROM Historial h 
GROUP BY h.idProducto 
ORDER BY COUNT(h) DESC
""")
List<Object[]> productosMasEscaneados();


@Query("""
SELECT h.idUsuario, COUNT(h) 
FROM Historial h 
GROUP BY h.idUsuario 
ORDER BY COUNT(h) DESC
""")
List<Object[]> rankingTrabajadores();



@Query("""
SELECT h.idProducto, COUNT(h) 
FROM Historial h 
WHERE h.idUsuario = :idUsuario
GROUP BY h.idProducto 
ORDER BY COUNT(h) DESC
""")
List<Object[]> productosMasEscaneadosPorUsuario(@Param("idUsuario") Long idUsuario);


Page<Historial> findByIdUsuarioOrderByFechaEscaneoDescHoraEscaneoDesc(
    Long idUsuario,
    Pageable pageable
);

}
