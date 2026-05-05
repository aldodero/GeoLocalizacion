package com.GeoMarket.Usuario_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.GeoMarket.Usuario_Service.dto.LoginRequest;
import com.GeoMarket.Usuario_Service.dto.LoginResponse;
import com.GeoMarket.Usuario_Service.model.TipoUsuario;
import com.GeoMarket.Usuario_Service.model.Usuario;
import com.GeoMarket.Usuario_Service.repository.TipoUsuarioRepository;
import com.GeoMarket.Usuario_Service.repository.UsuarioRepository;





@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;



    //Validaciones

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(campo + " inválido");
        }
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
    }

    
    //crear

    public Usuario crearUsuario(Usuario u, Long tipoUsuarioId) {

        validarTexto(u.getCorreo(), "Correo");
        validarTexto(u.getContrasena(), "Contraseña");

        if (usuarioRepository.existsByCorreo(u.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        if (usuarioRepository.existsByRutUsuario(u.getRutUsuario())) {
            throw new RuntimeException("El RUT ya está registrado");
        }

        TipoUsuario tipo = tipoUsuarioRepository.findById(tipoUsuarioId)
                .orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado"));

        u.setTipoUsuario(tipo);
        u.setContrasena(passwordEncoder.encode(u.getContrasena()));
        u.setActivo(true);

        return usuarioRepository.save(u);
    }



    //obtener user por id

    public Usuario obtenerPorId(Long id) {
        validarId(id);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }


    //listar todos los user
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }



    //actualizar usuario por id
    public Usuario actualizar(Long id, Usuario datos) {

        Usuario u = obtenerPorId(id);

        if (datos.getCorreo() != null) {
            u.setCorreo(datos.getCorreo());
        }

        if (datos.getContrasena() != null) {
            u.setContrasena(passwordEncoder.encode(datos.getContrasena()));
        }

        u.setPrimerNombre(datos.getPrimerNombre());
        u.setPrimerApellido(datos.getPrimerApellido());

        return usuarioRepository.save(u);
    }


    //eliminar user por id
    public void eliminar(Long id) {
        validarId(id);
        usuarioRepository.deleteById(id);
    }

    


    //login
    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtService.generarToken(usuario.getCorreo());

        return new LoginResponse(
                token,
                usuario.getCorreo(),
                usuario.getTipoUsuario().getNombreTipoUsuario()
        );
    }

    


    //verificaciones
    public boolean existePorCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    public boolean existePorRut(String rut) {
        return usuarioRepository.existsByRutUsuario(rut);
    }



    public String resumen() {

    long total = usuarioRepository.count();

    // generales
    long activos = usuarioRepository.countByActivo(true);
    long inactivos = usuarioRepository.countByActivo(false);

    // por rol
    long admin = usuarioRepository.countByTipoUsuario_RolUsuario("ROLE_ADMIN");
    long supervisor = usuarioRepository.countByTipoUsuario_RolUsuario("ROLE_SUPERVISOR");
    long trabajador = usuarioRepository.countByTipoUsuario_RolUsuario("ROLE_TRABAJADOR");

    // activos por rol
    long adminActivos = usuarioRepository.countByTipoUsuario_RolUsuarioAndActivo("ROLE_ADMIN", true);
    long supervisorActivos = usuarioRepository.countByTipoUsuario_RolUsuarioAndActivo("ROLE_SUPERVISOR", true);
    long trabajadorActivos = usuarioRepository.countByTipoUsuario_RolUsuarioAndActivo("ROLE_TRABAJADOR", true);

    // inactivos por rol
    long adminInactivos = usuarioRepository.countByTipoUsuario_RolUsuarioAndActivo("ROLE_ADMIN", false);
    long supervisorInactivos = usuarioRepository.countByTipoUsuario_RolUsuarioAndActivo("ROLE_SUPERVISOR", false);
    long trabajadorInactivos = usuarioRepository.countByTipoUsuario_RolUsuarioAndActivo("ROLE_TRABAJADOR", false);

    return """
        ===== RESUMEN USUARIOS =====
        Total usuarios: %d

        🔹 ADMIN: %d (Activos: %d | Inactivos: %d)
        🔹 SUPERVISOR: %d (Activos: %d | Inactivos: %d)
        🔹 TRABAJADOR: %d (Activos: %d | Inactivos: %d)

            Activos: %d
            Inactivos: %d
        """.formatted(
            total,
            admin, adminActivos, adminInactivos,
            supervisor, supervisorActivos, supervisorInactivos,
            trabajador, trabajadorActivos, trabajadorInactivos,
            activos, inactivos
        );
}



}