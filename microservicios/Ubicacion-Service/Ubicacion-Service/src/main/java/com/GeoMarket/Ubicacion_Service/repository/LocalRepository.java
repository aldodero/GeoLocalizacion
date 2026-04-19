package com.GeoMarket.Ubicacion_Service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GeoMarket.Ubicacion_Service.model.Local;

@Repository
public interface  LocalRepository extends JpaRepository<Local, Long> {

    //BUSCAR POR NOMBRE DE LOCAL
    List<Local> findByNombreLocalContainingIgnoreCase(String Nombre);

    //BUSCAR LOCAL CON CODIGO DE LOCAL
    Optional<Local>findByCodigoLocalIgnoreCase(String CodigoLocal);

    //VERIFICAR SI EXISTE LOCAL POR CODIGO DE LOCAL
    Boolean  existsByCodigoLocal(String codigoLocal);

    //BUSCAR LOCALES POR GEOLOCALIZAION
    List<Local> findByLatitudAndLongitud(Double latitud, Double longitud);

    //CONSULTA A LA BD
    @Query("SELECT l FROM Local l WHERE " +
       "(6371 * acos(cos(radians(:lat)) * cos(radians(l.latitud)) * " +
       "cos(radians(l.longitud) - radians(:lng)) + sin(radians(:lat)) * " +
       "sin(radians(l.latitud)))) < :distancia")

    //REPONEDOR PUEDA BUSCAR LOCALES CERCANOS A TRAVES DE SU APP 
    List<Local> findLocalesCercanos(
        @Param("lat") Double lat,
        @Param("lng") Double lng,
        @Param("distancia") Double distancia


);
    //CONTRAR LOCALES POR COMUNA SERVICE COMUNA
    Long countByComuna_IdComuna(Long idComuna);

    //OBTENER LOCALES POR COMUNA
    List<Local> findByComuna_IdComuna(Long idComuna);

    // CONTAR LOCALES POR CIUDAD
    Long countByComuna_Ciudad_IdCiudad(Long idCiudad);


    //OBTENER LOCALES POR CIUDAD
    List<Local> findByComuna_Ciudad_IdCiudad(Long idCiudad);


    //CONTAR LOCALES POR REGION
    Long countByComuna_Ciudad_Region_IdRegion(Long idRegion);

    //OBTENER LOCALES POR REGION
    List<Local> findByComuna_Ciudad_Region_IdRegion(Long idRegion);
}
