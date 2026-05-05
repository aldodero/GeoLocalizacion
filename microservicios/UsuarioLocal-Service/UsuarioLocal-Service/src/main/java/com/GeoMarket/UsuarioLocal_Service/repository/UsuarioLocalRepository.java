package com.GeoMarket.UsuarioLocal_Service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GeoMarket.UsuarioLocal_Service.model.UsuarioLocal;

public interface UsuarioLocalRepository extends JpaRepository<UsuarioLocal, Long>{



    List<UsuarioLocal> findByIdUsuario(Long idUsuario);

    List<UsuarioLocal> findByIdLocal(Long idLocal);

    boolean existsByIdUsuarioAndIdLocal(Long idUsuario, Long idLocal);
}
