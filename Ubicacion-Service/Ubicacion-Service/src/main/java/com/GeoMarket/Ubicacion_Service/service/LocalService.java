package com.GeoMarket.Ubicacion_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GeoMarket.Ubicacion_Service.model.Local;
import com.GeoMarket.Ubicacion_Service.repository.LocalRepository;

@Service
public class LocalService {

    @Autowired
    private LocalRepository localrepository;

    // ==============================
    // 🔒 VALIDACIONES
    // ==============================

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
    }

    private void validarLocal(Local l) {
        if (l == null) {
            throw new IllegalArgumentException("Local obligatorio");
        }

        if (l.getNombreLocal() == null || l.getNombreLocal().isBlank()) {
            throw new IllegalArgumentException("Nombre inválido");
        }

        if (l.getCodigoLocal() == null || l.getCodigoLocal().isBlank()) {
            throw new IllegalArgumentException("Código obligatorio");
        }

        if (l.getDireccionLocal() == null || l.getDireccionLocal().isBlank()) {
            throw new IllegalArgumentException("Dirección obligatoria");
        }

        if (l.getLatitud() == null || l.getLongitud() == null) {
            throw new IllegalArgumentException("Ubicación obligatoria");
        }

        if (l.getComuna() == null || l.getComuna().getIdComuna() == null) {
            throw new IllegalArgumentException("Comuna obligatoria");
        }
    }

    // ==============================
    // 👷 TRABAJADOR
    // ==============================

    // Obtener por ubicación exacta
    public List<Local> obtenerPorUbicacion(Double lat, Double lng) {
        if (lat == null || lng == null) {
            throw new IllegalArgumentException("Coordenadas inválidas");
        }
        return localrepository.findByLatitudAndLongitud(lat, lng);
    }

    // Obtener locales cercanos
    public List<Local> obtenerLocalesCercanos(Double lat, Double lng, Double distancia) {
        if (lat == null || lng == null || distancia == null || distancia <= 0) {
            throw new IllegalArgumentException("Parámetros inválidos");
        }
        return localrepository.findLocalesCercanos(lat, lng, distancia);
    }

    // Obtener local por ID
    public Local obtenerPorId(Long idLocal) {
        validarId(idLocal);
        return localrepository.findById(idLocal)
                .orElseThrow(() -> new RuntimeException("Local no encontrado"));
    }

    // Obtener por código
    public Local obtenerPorCodigo(String codigoLocal) {
        if (codigoLocal == null || codigoLocal.isBlank()) {
            throw new IllegalArgumentException("Código inválido");
        }

        return localrepository.findByCodigoLocalIgnoreCase(codigoLocal)
                .orElseThrow(() -> new RuntimeException("Local no encontrado"));
    }

    // ==============================
    // 🧑‍💼 ADMIN (CRUD)
    // ==============================

    public Local crearLocal(Local l) {

        validarLocal(l);

        if (localrepository.existsByCodigoLocal(l.getCodigoLocal())) {
            throw new RuntimeException("El código del local ya existe");
        }

        return localrepository.save(l);
    }

    public List<Local> listarLocales() {
        return localrepository.findAll();
    }

    public Local actualizarLocal(Long idLocal, Local datos) {

        validarId(idLocal);

        Local existente = obtenerPorId(idLocal);

        if (datos.getNombreLocal() != null) {
            existente.setNombreLocal(datos.getNombreLocal());
        }

        if (datos.getDireccionLocal() != null) {
            existente.setDireccionLocal(datos.getDireccionLocal());
        }

        if (datos.getLatitud() != null) {
            existente.setLatitud(datos.getLatitud());
        }

        if (datos.getLongitud() != null) {
            existente.setLongitud(datos.getLongitud());
        }

        if (datos.getCodigoLocal() != null) {

            if (localrepository.existsByCodigoLocal(datos.getCodigoLocal())) {
                throw new RuntimeException("El código ya existe");
            }

            existente.setCodigoLocal(datos.getCodigoLocal());
        }

        return localrepository.save(existente);
    }



    public void eliminarLocal(Long idLocal) {

        validarId(idLocal);

        if (!localrepository.existsById(idLocal)) {
            throw new RuntimeException("Local no existe");
        }

        localrepository.deleteById(idLocal);
    }

    // ==============================
    // 🔍 FILTROS
    // ==============================

    public List<Local> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre inválido");
        }
        return localrepository.findByNombreLocalContainingIgnoreCase(nombre);
    }

    // ==============================
    // 🔎 VALIDACIONES / EXISTENCIA
    // ==============================

    public Boolean existePorId(Long idLocal) {
        if (idLocal == null) return false;
        return localrepository.existsById(idLocal);
    }

    public Boolean existePorCodigo(String codigoLocal) {
        if (codigoLocal == null) return false;
        return localrepository.existsByCodigoLocal(codigoLocal);
    }

   

    //resumen local
    public String resumenLocal(Long idLocal) {

    validarId(idLocal);

    try {
        Local l = obtenerPorId(idLocal);

        return "Local: " + l.getNombreLocal()
                + " | Código: " + l.getCodigoLocal()
                + " | Dirección: " + l.getDireccionLocal();

    } catch (RuntimeException e) {
        return "Local con id " + idLocal + " no existe";
    }

    }









}