package Geomarket.Evento_Service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Geomarket.Evento_Service.model.TipoEvento;

@Repository
public interface TipoEventoRepository extends  JpaRepository<TipoEvento, Long>{

}
