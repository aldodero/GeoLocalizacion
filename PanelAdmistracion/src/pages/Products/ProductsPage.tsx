import {
  Box,
  TextField,
} from "@mui/material";
import { useEffect, useState } from "react";
import axios from "axios";

import AdminLayout from "../../layouts/AdminLayout";

export default function ProductsPage() {
  const [productos, setProductos] = useState<any[]>([]);
  const [busqueda, setBusqueda] = useState("");





  useEffect(() => {
    const cargarProductos = async () => {
      try {
        const idLocal = localStorage.getItem("idLocal");

        if (!idLocal) return;

        const response = await axios.get(
          `http://localhost:8088/api/productoLocal/listar/${idLocal}`
        );
       

        setProductos(response.data);
      } catch (error) {
        console.error(
          "Error cargando productos",
          error
        );
      }
    };

    cargarProductos();

    window.addEventListener(
      "localChanged",
      cargarProductos
    );

    return () => {
      window.removeEventListener(
        "localChanged",
        cargarProductos
      );
    };
  },[]);



  
  const productosFiltrados = productos.filter(
    (producto) =>
      producto.nombreProducto
        ?.toLowerCase()
        .includes(busqueda.toLowerCase()) ||
      producto.codigoProducto
        ?.toLowerCase()
        .includes(busqueda.toLowerCase())
  );

  return (
    <AdminLayout>
      <TextField
        fullWidth
        placeholder="Buscar producto..."
        value={busqueda}
        onChange={(e) =>
          setBusqueda(e.target.value)
        }
        sx={{
          mb: 3,
        }}
      />

      <Box
        sx={{
          backgroundColor: "#FFFFFF",
          borderRadius: 4,
          p: 3,
        }}
      >
        <Box
          sx={{
            display: "grid",
            gridTemplateColumns:
              "1fr 3fr 1fr 1fr 1fr 1fr",
            gap: 2,
            pb: 2,
            borderBottom:
              "1px solid #E0E0E0",
            fontWeight: 700,
          }}
        >
          <div>Código</div>
          <div>Producto</div>
          <div>Tipo</div>
          <div>Precio</div>
          <div>Stock</div>
          <div>Estado</div>
        </Box>

        {productosFiltrados.map((producto) => (
          <Box
            key={producto.idProducto}
            sx={{
              display: "grid",
              gridTemplateColumns:
                "1fr 3fr 1fr 1fr 1fr 1fr",
              gap: 2,
              py: 2,
              borderBottom:
                "1px solid #F0F0F0",
            }}
          >
            <div>{producto.codigoProducto}</div>
            <div>{producto.nombreProducto}</div>
            <div>{producto.tipo}</div>
            <div>${producto.precio}</div>
            <div>{producto.stock}</div>
            <div>{producto.estado}</div>
          </Box>
        ))}
      </Box>
    </AdminLayout>
  );
}