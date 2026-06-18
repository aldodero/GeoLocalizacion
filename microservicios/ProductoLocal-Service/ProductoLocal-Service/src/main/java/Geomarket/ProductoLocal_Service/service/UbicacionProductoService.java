package Geomarket.ProductoLocal_Service.service;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Geomarket.ProductoLocal_Service.dto.EscaneoResponse;
import Geomarket.ProductoLocal_Service.model.UbicacionProducto;
import Geomarket.ProductoLocal_Service.repository.UbicacionProductoRepository;
import Geomarket.ProductoLocal_Service.service.client.ProductoClient;



@Service
public class UbicacionProductoService {

    @Autowired
    private UbicacionProductoRepository ubicacionproductoRepository;
    
    @Autowired
    private ProductoClient productoclient;
  


 
    //VALIDAR ID
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

    private void validarUbicacion(UbicacionProducto u) {
        if (u == null) {
            throw new IllegalArgumentException("Ubicación obligatoria");
        }

        validarTexto(u.getPasillo(), "Pasillo");
        validarTexto(u.getSeccion(), "Sección");
        validarTexto(u.getGondola(), "Góndola");
    }

  

    //CREAR UBI DE PRODUCTO
    public UbicacionProducto crear(UbicacionProducto u) {

        validarUbicacion(u);

        return ubicacionproductoRepository.save(u);
    }

    public List<UbicacionProducto> listar() {
        return ubicacionproductoRepository.findAll();
    }



    //OBTENER UBI POR ID 
    public UbicacionProducto obtenerPorId(Long id) {

        validarId(id);

        return ubicacionproductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));
    }




    //ACTUALIZAR UBI POR ID 
    public UbicacionProducto actualizar(Long id, UbicacionProducto datos) {

        validarId(id);

        UbicacionProducto existente = obtenerPorId(id);

        if (datos.getPasillo() != null) {
            existente.setPasillo(datos.getPasillo());
        }

        if (datos.getSeccion() != null) {
            existente.setSeccion(datos.getSeccion());
        }

        if (datos.getGondola() != null) {
            existente.setGondola(datos.getGondola());
        }

        return ubicacionproductoRepository.save(existente);
    }



    //ELIMIAR UBI POR ID
    public void eliminar(Long id) {

        validarId(id);

        if (!ubicacionproductoRepository.existsById(id)) {
            throw new RuntimeException("Ubicación no existe");
        }

        ubicacionproductoRepository.deleteById(id);
    }

   






    public UbicacionProducto obtenerUbicacionPorCodigo(String codigoProducto) {
        validarTexto(codigoProducto,"Código");

        Map<String, Object> producto =productoclient.buscarPorCodigo(codigoProducto);

        if (producto == null) {

            throw new RuntimeException(
                    "Producto no encontrado"
            );
        }
        Long idProducto =Long.valueOf(producto.get("idProducto").toString());

        List<UbicacionProducto> ubicaciones =
                ubicacionproductoRepository
                        .findByProductolocal_IdProducto(
                                idProducto);

        if (ubicaciones.isEmpty()) {
            throw new RuntimeException(
                    "El producto no tiene ubicación asignada"
            );
        }
        return ubicaciones.get(0);
}





    // EXTO PARA APP
    public String obtenerUbicacionTexto(String codigoProducto) {

        UbicacionProducto u = obtenerUbicacionPorCodigo(codigoProducto);

        return "Pasillo: " + u.getPasillo()
                + " | Sección: " + u.getSeccion()
                + " | Góndola: " + u.getGondola();
    }




    //FORMATO MAPA
    public String ubicacionParaMapa(String codigoProducto) {

        UbicacionProducto u = obtenerUbicacionPorCodigo(codigoProducto);

        return "Dirígete al pasillo " + u.getPasillo()
                + ", sección " + u.getSeccion()
                + ", góndola " + u.getGondola();
    }

   



    //FILTROS///
    public List<UbicacionProducto> buscarPorPasillo(String pasillo) {
        validarTexto(pasillo, "Pasillo");
        return ubicacionproductoRepository.findByPasilloIgnoreCase(pasillo);
    }

    public List<UbicacionProducto> buscarPorSeccion(String seccion) {
        validarTexto(seccion, "Sección");
        return ubicacionproductoRepository.findBySeccionIgnoreCase(seccion);
    }

    public List<UbicacionProducto> buscarPorGondola(String gondola) {
        validarTexto(gondola, "Góndola");
        return ubicacionproductoRepository.findByGondolaIgnoreCase(gondola);
    }






    //VALIDACION///
    public Boolean existePorId(Long id) {
        if (id == null) return false;
        return ubicacionproductoRepository.existsById(id);
    }

    


    //CONTEO
    public Long contarUbicaciones() {
        return ubicacionproductoRepository.count();
    }


    //RESUMEN GENERAL
    public String resumen() {
        Long total = contarUbicaciones();
        return "Ubicaciones → Total: " + total;
    }






   public EscaneoResponse obtenerProductoEscaneado(
        String codigo,
        Long idLocal
) {

    validarTexto(codigo, "Código");

    Map<String, Object> producto =
            productoclient.buscarPorCodigo(
                    codigo
            );

    if (producto == null) {
        throw new RuntimeException(
                "Producto no encontrado"
        );
    }

    Long idProducto =
            Long.valueOf(
                    producto.get("idProducto")
                            .toString()
            );

    List<UbicacionProducto> ubicaciones =
            ubicacionproductoRepository
                    .findByProductolocal_IdProductoAndProductolocal_IdLocal(
                            idProducto,
                            idLocal
                    );
        System.out.println(
            "ID PRODUCTO = " + idProducto
    );

    System.out.println(
            "ID LOCAL = " + idLocal
    );

    System.out.println(
            "UBICACIONES ENCONTRADAS = "
                    + ubicaciones.size()
    );

    if (ubicaciones.isEmpty()) {
        throw new RuntimeException(
                "Producto no existe en este local"
        );
    }

    UbicacionProducto ubicacion =
            ubicaciones.get(0);

    String ubicacionTexto =
            "Pasillo "
                    + ubicacion.getPasillo()
                    + " - "
                    + ubicacion.getSeccion()
                    + " - Góndola "
                    + ubicacion.getGondola();

    return new EscaneoResponse(
            idProducto,
            producto.get("nombreProducto").toString(),
            producto.get("codigoProducto").toString(),
            ubicacionTexto,
            idLocal
    );
}


}
