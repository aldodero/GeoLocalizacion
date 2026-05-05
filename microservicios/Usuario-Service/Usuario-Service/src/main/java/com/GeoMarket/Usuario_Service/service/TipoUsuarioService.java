package com.GeoMarket.Usuario_Service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.GeoMarket.Usuario_Service.model.TipoUsuario;
import com.GeoMarket.Usuario_Service.repository.TipoUsuarioRepository;

@Service
public class TipoUsuarioService {

    private final TipoUsuarioRepository repository;

    public TipoUsuarioService(TipoUsuarioRepository repository) {
        this.repository = repository;
    }

   
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
    }

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(campo + " inválido");
        }
    }

   


    public TipoUsuario crearTipoUsuario(TipoUsuario tipo) {

        validarTexto(tipo.getRolUsuario(), "Rol");

        String rol = tipo.getRolUsuario().toUpperCase();

        if (repository.findByRolUsuario(rol).isPresent()) {
            throw new RuntimeException("El rol ya existe");
        }

        tipo.setRolUsuario(rol);

        return repository.save(tipo);
    }

    public TipoUsuario obtenerPorId(Long id) {
        validarId(id);

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoUsuario no encontrado"));
    }




    public List<TipoUsuario> listar() {
        return repository.findAll();
    }




    public TipoUsuario actualizar(Long id, TipoUsuario datos) {

        validarId(id);
        validarTexto(datos.getRolUsuario(), "Rol");

        TipoUsuario existente = obtenerPorId(id);

        String nuevoRol = datos.getRolUsuario().toUpperCase();

        if (!existente.getRolUsuario().equals(nuevoRol) &&
            repository.findByRolUsuario(nuevoRol).isPresent()) {
            throw new RuntimeException("Ya existe ese rol");
        }

        existente.setRolUsuario(nuevoRol);
        existente.setDescripcion(datos.getDescripcion());

        return repository.save(existente);
    }





    public void eliminar(Long id) {
        validarId(id);

        if (!repository.existsById(id)) {
            throw new RuntimeException("TipoUsuario no existe");
        }

        repository.deleteById(id);
    }




   
    public TipoUsuario obtenerPorRol(String rol) {
        validarTexto(rol, "Rol");

        return repository.findByRolUsuario(rol.toUpperCase())
                .orElseThrow(() -> new RuntimeException("TipoUsuario no encontrado"));
    }




    public boolean existePorRol(String rol) {
        if (rol == null) return false;
        return repository.findByRolUsuario(rol.toUpperCase()).isPresent();
    }



    public Long contar() {
        return repository.count();
    }



public TipoUsuario cambiarEstado(Long id, Boolean estado) {
    TipoUsuario tipo = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

    tipo.setEstado(estado);
    return repository.save(tipo);
}




public List<TipoUsuario> buscarPorEstado(Boolean estado) {
    return repository.findByEstado(estado);
}




   public String resumen() {
    long total = repository.count();
    // Totales por rol
    long admin = repository.countByRolUsuario("ROLE_ADMIN");
    long supervisor = repository.countByRolUsuario("ROLE_SUPERVISOR");
    long trabajador = repository.countByRolUsuario("ROLE_TRABAJADOR");

    // Activos / inactivos generales
    long activos = repository.countByEstado(true);
    long inactivos = repository.countByEstado(false);
    // Activos por rol
    long adminActivos = repository.countByRolUsuarioAndEstado("ROLE_ADMIN", true);
    long supervisorActivos = repository.countByRolUsuarioAndEstado("ROLE_SUPERVISOR", true);
    long trabajadorActivos = repository.countByRolUsuarioAndEstado("ROLE_TRABAJADOR", true);
    // Inactivos por rol
    long adminInactivos = repository.countByRolUsuarioAndEstado("ROLE_ADMIN", false);
    long supervisorInactivos = repository.countByRolUsuarioAndEstado("ROLE_SUPERVISOR", false);
    long trabajadorInactivos = repository.countByRolUsuarioAndEstado("ROLE_TRABAJADOR", false);

    return """
        ===== RESUMEN TIPOS USUARIO =====
        Total: %d

        🔹 ADMIN: %d (Activos: %d | Inactivos: %d)
        🔹 SUPERVISOR: %d (Activos: %d | Inactivos: %d)
        🔹 TRABAJADOR: %d (Activos: %d | Inactivos: %d)

        Activos totales: %d
        Inactivos totales: %d
        """.formatted(
            total,
            admin, adminActivos, adminInactivos,
            supervisor, supervisorActivos, supervisorInactivos,
            trabajador, trabajadorActivos, trabajadorInactivos,
            activos, inactivos
        );
}








}





