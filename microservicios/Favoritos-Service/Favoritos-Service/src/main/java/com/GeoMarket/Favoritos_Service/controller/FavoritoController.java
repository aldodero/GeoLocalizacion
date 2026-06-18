package com.GeoMarket.Favoritos_Service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GeoMarket.Favoritos_Service.model.Favorito;
import com.GeoMarket.Favoritos_Service.service.FavoritoService;
import com.GeoMarket.Favoritos_Service.service.client.ProductoClient;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {





private final FavoritoService favoritoservice;
private final ProductoClient productoClient;

public FavoritoController(
        FavoritoService favoritoservice,
        ProductoClient productoClient
) {
    this.favoritoservice = favoritoservice;
    this.productoClient = productoClient;
}
    


    //GUARDAR FAVORITO
    @PostMapping("/guardar")
public Favorito guardar(@RequestBody Map<String, Object> data) {

    Long idUsuario =Long.valueOf(data.get("idUsuario").toString());
    Long idProducto =Long.valueOf(data.get("idProducto").toString());
    Long idLocal = Long.valueOf(data.get("idLocal").toString());

    return favoritoservice.guardarFavorito(idUsuario, idProducto, idLocal);
}



  //LISTAR TODOS LOS FAVORITOS
    @GetMapping("/listar")
    public List<Map<String, Object>> obtenerLista() {

        List<Favorito> lista =
                favoritoservice.obtenerFavoritos();

        return lista.stream().map(f -> {

            Map<String, Object> map = new HashMap<>();

            map.put("idFavorito", f.getIdFavorito());
            map.put("idProducto", f.getIdProducto());

            // 🔥 consultar Producto-Service
            Map<String, Object> producto =
                    productoClient.obtenerProducto(
                            f.getIdProducto()
                    );

            if (producto != null) {

                map.put(
                    "nombreProducto",
                    producto.get("nombreProducto")
                );

                map.put(
                    "codigoProducto",
                    producto.get("codigoProducto")
                );

                map.put(
                    "tipo",
                    producto.get("tipo")
                );

            } else {

                map.put("nombreProducto", "Sin nombre");
                map.put("codigoProducto", "-");
                map.put("tipo", "-");
            }

            return map;

        }).toList();
}


//lista filtrada de favoritos por id and user
@GetMapping("/listar/{idUsuario}/{idLocal}")
public List<Map<String, Object>> obtenerListaPorLocal(

        @PathVariable Long idUsuario,

        @PathVariable Long idLocal
) {

    List<Favorito> lista =
            favoritoservice
            .obtenerFavoritosPorLocal(
                    idUsuario,
                    idLocal
            );

    return lista.stream().map(f -> {

        Map<String, Object> map =
                new HashMap<>();

        map.put(
                "idFavorito",
                f.getIdFavorito()
        );

        map.put(
                "idProducto",
                f.getIdProducto()
        );

        Map<String, Object> producto =
                productoClient.obtenerProducto(
                        f.getIdProducto()
                );
        System.out.println(
        "PRODUCTO FAVORITO = " + producto);

        if (producto != null) {

            map.put(
                    "nombreProducto",
                    producto.get("nombreProducto")
            );

            map.put(
                    "codigoProducto",
                    producto.get("codigoProducto")
            );

            map.put(
                    "tipo",
                    producto.get("tipo")
            );

        } else {

            map.put(
                    "nombreProducto",
                    "Sin nombre"
            );

            map.put(
                    "codigoProducto",
                    "-"
            );

            map.put(
                    "tipo",
                    "-"
            );
        }

        return map;

    }).toList();
}



    // ELIMINAR FAVORITO
@DeleteMapping("/eliminar")
public ResponseEntity<String> eliminarFavorito( @RequestParam Long idUsuario,@RequestParam Long idProducto
) {
    favoritoservice.eliminarFavorito(idUsuario, idProducto);
    return ResponseEntity.ok(
            "Favorito eliminado correctamente"
    );
}


    //VERIFICAR SI EXISTE
    @GetMapping("/existe{idUsuario}")
    public boolean esFavorito(@RequestParam Long idUsuario,@RequestParam Long idProducto) {
        return favoritoservice.esFavorito(idUsuario, idProducto);
    }



}

