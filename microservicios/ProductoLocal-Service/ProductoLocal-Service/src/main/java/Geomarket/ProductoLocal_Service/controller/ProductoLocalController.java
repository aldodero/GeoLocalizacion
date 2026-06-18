package Geomarket.ProductoLocal_Service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Geomarket.ProductoLocal_Service.model.ProductoLocal;
import Geomarket.ProductoLocal_Service.service.ProductoLocalService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("api/productoLocal")
public class ProductoLocalController {

    @Autowired
    private ProductoLocalService productolocalservice;


        ///validar id
        @GetMapping("/validar/{idProductoLocal}")
        public void validarId(@PathVariable Long idProductoLocal){
            productolocalservice.validarId(idProductoLocal);
    }

        //listar
        @GetMapping("/listar")
        public List<ProductoLocal> listar(){
            return productolocalservice.listar();
        }


        //crear
        @PostMapping("/crear")
        public ProductoLocal crear(@RequestBody ProductoLocal prod){
            return productolocalservice.crear(prod);
        }


        //eliminar
        @DeleteMapping("/Eliminar/{idProductoLocal}")
        public String Eliminar(@PathVariable Long idProductoLocal){
            return productolocalservice.ElimiarPorId(idProductoLocal);
        }

        //actualizar info
        @PutMapping("/Actualizar/{idProductoLocal}")
        public String actualizar(@PathVariable Long  idProductoLocal, @RequestBody ProductoLocal prod){
            return productolocalservice.ActualizarInfo(idProductoLocal, prod);
        }
        

        @GetMapping("/listar/{idLocal}")
        public List<Map<String,Object>> listarPorId(@PathVariable Long idLocal){
            return productolocalservice.listarPorLocal(idLocal);
        }   

        @GetMapping("/contarProductos/Local/{idLocal}")
        public Long contarProductoLocal(@PathVariable Long idLocal){
            return productolocalservice.contarProductosPorLocal(idLocal);
        }
        
        @GetMapping("/resumen/{idLocal}")
        public Map<String,Object> resumen(@PathVariable Long idLocal){
            return productolocalservice.resumenLocal(idLocal);
        }

        @GetMapping("/buscar/{idLocal}/{nombre}")
        public List<Map<String, Object>> buscarPorNombreYLocal(@PathVariable Long idLocal,@PathVariable String nombre) {
            return productolocalservice.buscarPorNombreYLocal(idLocal,nombre);
        }




}
