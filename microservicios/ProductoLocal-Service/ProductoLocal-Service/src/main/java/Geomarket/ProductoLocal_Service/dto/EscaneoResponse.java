package Geomarket.ProductoLocal_Service.dto;

public class EscaneoResponse {

    private Long idProducto;
    private String nombreProducto;
    private String codigoProducto;
    private String ubicacion;
    private Long idLocal;

    public EscaneoResponse(
            Long idProducto,
            String nombreProducto,
            String codigoProducto,
            String ubicacion,
            Long idLocal
    ) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.codigoProducto = codigoProducto;
        this.ubicacion = ubicacion;
        this.idLocal = idLocal;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Long getIdLocal() {
        return idLocal;
    }
}