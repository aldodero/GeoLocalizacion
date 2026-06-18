import {
  Box,
  TextField,
} from "@mui/material";

import AdminLayout from "../../layouts/AdminLayout";

export default function ReportsPage() {
  return (
    <AdminLayout>
      <TextField
        fullWidth
        placeholder="Buscar reporte..."
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
              "2fr 3fr 1fr 1fr",
            gap: 2,
            pb: 2,
            borderBottom: "1px solid #E0E0E0",
            fontWeight: 700,
          }}
        >
          <div>Título</div>
          <div>Trabajador</div>
          <div>Fecha</div>
          <div>Estado</div>
        </Box>

        <Box
          sx={{
            display: "grid",
            gridTemplateColumns:
              "2fr 3fr 1fr 1fr",
            gap: 2,
            py: 2,
            borderBottom: "1px solid #F0F0F0",
          }}
        >
          <div>Producto sin ubicación</div>
          <div>Juan Pérez</div>
          <div>20/06/2025</div>
          <div>Pendiente</div>
        </Box>

        <Box
          sx={{
            display: "grid",
            gridTemplateColumns:
              "2fr 3fr 1fr 1fr",
            gap: 2,
            py: 2,
            borderBottom: "1px solid #F0F0F0",
          }}
        >
          <div>Stock incorrecto</div>
          <div>Ana Soto</div>
          <div>19/06/2025</div>
          <div>En revisión</div>
        </Box>

        <Box
          sx={{
            display: "grid",
            gridTemplateColumns:
              "2fr 3fr 1fr 1fr",
            gap: 2,
            py: 2,
          }}
        >
          <div>Código de barra dañado</div>
          <div>Carlos Díaz</div>
          <div>18/06/2025</div>
          <div>Resuelto</div>
        </Box>
      </Box>
    </AdminLayout>
  );
}