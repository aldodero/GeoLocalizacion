package com.GeoMarket.Usuario_Service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GeoMarket.Usuario_Service.model.Usuario;



public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //buscar usuario por rut
    Optional<Usuario> findByRutUsuario(String rutUsuario);

    //buscar por correo
    List<Usuario> findByCorreoContainingIgnoreCase(String correo);

    //verificar si existe por gmail
    boolean existsByCorreo(String correo);

    //verificar si existe por rut
    boolean existsByRutUsuario(String rutUsuario);

    Optional<Usuario> findByCorreo(String correo);


    long countByActivo(boolean activo);

    long countByTipoUsuario_RolUsuario(String rolUsuario);

    long countByTipoUsuario_RolUsuarioAndActivo(String rolUsuario, boolean activo);

    
}
