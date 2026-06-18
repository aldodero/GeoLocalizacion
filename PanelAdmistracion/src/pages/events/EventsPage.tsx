import {
  Box,
  TextField,
} from "@mui/material";

import AdminLayout from "../../layouts/AdminLayout";

export default function EventsPage() {
  return (
    <AdminLayout>
      <TextField
        fullWidth
        placeholder="Buscar evento..."
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
              "2fr 2fr 1fr 1fr",
            gap: 2,
            pb: 2,
            borderBottom: "1px solid #E0E0E0",
            fontWeight: 700,
          }}
        >
          <div>Evento</div>
          <div>Descripción</div>
          <div>Fecha</div>
          <div>Estado</div>
        </Box>

        <Box
          sx={{
            display: "grid",
            gridTemplateColumns:
              "2fr 2fr 1fr 1fr",
            gap: 2,
            py: 2,
            borderBottom: "1px solid #F0F0F0",
          }}
        >
          <div>Inspección de seguridad</div>
          <div>Revisión de extintores y salidas de emergencia</div>
          <div>22/06/2025</div>
          <div>Pendiente</div>
        </Box>

        <Box
          sx={{
            display: "grid",
            gridTemplateColumns:
              "2fr 2fr 1fr 1fr",
            gap: 2,
            py: 2,
            borderBottom: "1px solid #F0F0F0",
          }}
        >
          <div>Mantenimiento góndolas</div>
          <div>Reparación de góndola sección lácteos</div>
          <div>21/06/2025</div>
          <div>Atendido</div>
        </Box>

        <Box
          sx={{
            display: "grid",
            gridTemplateColumns:
              "2fr 2fr 1fr 1fr",
            gap: 2,
            py: 2,
          }}
        >
          <div>Capacitación</div>
          <div>Protocolo de atención al cliente</div>
          <div>20/06/2025</div>
          <div>Cerrado</div>
        </Box>
      </Box>
    </AdminLayout>
  );
}