package com.GeoMarket.Usuario_Service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GeoMarket.Usuario_Service.model.TipoUsuario;



public interface TipoUsuarioRepository extends  JpaRepository<TipoUsuario, Long> {

    Optional<TipoUsuario> findByRolUsuario(String rolUsuario);

    List<TipoUsuario> findByEstado(Boolean estado);


long countByRolUsuario(String rolUsuario);

long countByRolUsuarioAndEstado(String rolUsuario, Boolean estado);

long countByEstado(Boolean estado);


}












