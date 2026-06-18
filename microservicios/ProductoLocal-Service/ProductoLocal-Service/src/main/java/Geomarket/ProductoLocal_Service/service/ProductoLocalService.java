package Geomarket.ProductoLocal_Service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Geomarket.ProductoLocal_Service.model.ProductoLocal;
import Geomarket.ProductoLocal_Service.repository.ProductoLocalRepository;
import Geomarket.ProductoLocal_Service.service.client.LocalClient;
import Geomarket.ProductoLocal_Service.service.client.ProductoClient;


@Service
public class ProductoLocalService {
    

    @Autowired
    private ProductoLocalRepository productolocalrepository;

    @Autowired
    private ProductoClient productoclient;
    @Autowired
    private LocalClient localclient;



    //VALIDAR ID/////
    public void validarId(Long idProductoLocal){

        if(idProductoLocal == null || idProductoLocal <= 0){
            throw new IllegalArgumentException("id invalido");
        }
        if(!productolocalrepository.existsById(idProductoLocal)){
        throw new IllegalArgumentException("ID NO EXISTE");
        }
        
    }

    //listar relaciones
    public List<ProductoLocal> listar(){
        return productolocalrepository.findAll();
    }




    //crear una relacion entre producto y Local
    public ProductoLocal crear(ProductoLocal prod){

    if(productoclient.obtenerProducto(
            prod.getIdProducto()
    ) == null){
        throw new IllegalArgumentException(
                "El producto no existe"
        );
    }

    if(localclient.obtenerLocal(
            prod.getIdLocal()
    ) == null){
        throw new IllegalArgumentException(
                "El local no existe"
        );
    }

    prod.setFechaActualizacion(
            LocalDateTime.now()
    );

    return productolocalrepository.save(prod);
}





    //eliminar producto local relacion por id
    public String ElimiarPorId(Long idProductoLocal){
        if(!productolocalrepository.existsById(idProductoLocal)){
            return "id no existe";
        }
        productolocalrepository.deleteById(idProductoLocal);
        return "ProductoLocal eliminado con exito";
    }


    //actualizar ProductoLocal por id
    public String ActualizarInfo(Long idProductoLocal,  ProductoLocal nuevaInfo){

        ProductoLocal existente = productolocalrepository.findById(idProductoLocal)
         .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        
        if(nuevaInfo.getFechaActualizacion() != null){
            existente.setFechaActualizacion(nuevaInfo.getFechaActualizacion());
        }

        if(nuevaInfo.getStock() >= 0){
            existente.setStock(nuevaInfo.getStock());
        }
        if(nuevaInfo.getEstado() != null){
            existente.setEstado(nuevaInfo.getEstado());
        }
        productolocalrepository.save(existente);
        return "producto Local actualizado con exito";
    }




    //listar local por id
    public List<Map<String,Object>> listarPorLocal(Long idLocal){

    if(idLocal == null || idLocal <= 0){
        throw new IllegalArgumentException("id invalido");
    }

    List<ProductoLocal> productos =
            productolocalrepository.findByIdLocal(idLocal);

    List<Map<String,Object>> resultado =
            new ArrayList<>();

    for(ProductoLocal productoLocal : productos){

        Map<String,Object> producto =
                productoclient.obtenerProducto(
                        productoLocal.getIdProducto()
                        
                );
                System.out.println(producto);

        Map<String,Object> item =
                new HashMap<>();

        item.put(
                "idProducto",
                productoLocal.getIdProducto()
        );

        item.put(
                "nombreProducto",
                producto.get("nombreProducto")
        );

        item.put(
                "codigoProducto",
                producto.get("codigoProducto")
        );

        item.put(
                "tipo",
                producto.get("tipo")
        );

        item.put(
                "precio",
                producto.get("precio")
        );

        item.put(
                "stock",
                productoLocal.getStock()
        );

        item.put(
                "estado",
                productoLocal.getEstado()
        );

        resultado.add(item);
    }

    return resultado;
}


    //contar productos por local
    public Long contarProductosPorLocal(Long idLocal){
        if(idLocal == null || idLocal <= 0){
            throw new IllegalArgumentException("id local invalido");
        }
        return productolocalrepository.countByIdLocal(idLocal);
    }



    //resumen local
    public Map<String, Object> resumenLocal(
            Long idLocal
    ){

        Long cantidad =
                productolocalrepository
                        .countByIdLocal(idLocal);

        Double stock =
                productolocalrepository
                        .sumarStockPorLocal(idLocal);

        Map<String, Object> resumen =
                new HashMap<>();

        resumen.put("cantidad", cantidad);
        resumen.put("stock", stock);

        return resumen;
    }

    

    //BUQUEDA POR NOMBRE Y LOCAL
    public List<Map<String, Object>> buscarPorNombreYLocal(
        Long idLocal,
        String nombre
) {

    List<ProductoLocal> productosLocal =
            productolocalrepository.findByIdLocal(idLocal);

    List<Map<String, Object>> resultado =
            new ArrayList<>();

    for (ProductoLocal productoLocal : productosLocal) {

        Map<String, Object> producto =
                productoclient.obtenerProducto(
                        productoLocal.getIdProducto()
                );

        if (producto == null) {
            continue;
        }

        String nombreProducto =
                producto.get("nombreProducto")
                        .toString();

        if (nombreProducto
                .toLowerCase()
                .contains(nombre.toLowerCase())) {

            Map<String, Object> item =
                    new HashMap<>();

            item.put(
                    "idProducto",
                    productoLocal.getIdProducto()
            );

            item.put(
                    "nombreProducto",
                    producto.get("nombreProducto")
            );

            item.put(
                    "codigoProducto",
                    producto.get("codigoProducto")
            );

            item.put(
                    "tipo",
                    producto.get("tipo")
            );

            item.put(
                    "precio",
                    producto.get("precio")
            );

            item.put(
                    "stock",
                    productoLocal.getStock()
            );

            item.put(
                    "estado",
                    productoLocal.getEstado()
            );

            resultado.add(item);
        }
    }

    return resultado;
}


}





    









