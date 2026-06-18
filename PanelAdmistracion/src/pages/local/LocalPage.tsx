import { Box, Typography } from "@mui/material";
import { useNavigate, useParams } from "react-router-dom";

export default function LocalPage() {
  const navigate = useNavigate();
  const { id } = useParams();

  return (
    <Box
      sx={{
        display: "flex",
        minHeight: "100vh",
        backgroundColor: "#F5F5F5",
      }}
    >
      <Box
        sx={{
          width: 260,
          backgroundColor: "#1E1E1E",
          color: "#FFF",
          p: 3,
        }}
      >
        <Typography
          sx={{
            color: "#FF9800",
            fontSize: 30,
            fontWeight: 700,
            mb: 4,
          }}
        >
          GeoMarket
        </Typography>

        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            gap: 1,
          }}
        >
          <Box
            onClick={() =>
              navigate(`/local/${id}/workers`)
            }
            sx={{
              backgroundColor: "#FF9800",
              color: "#FFF",
              p: 2,
              borderRadius: 2,
              fontWeight: 600,
              cursor: "pointer",
            }}
          >
            👥 Trabajadores
          </Box>

          <Box
            onClick={() =>
              navigate(`/local/${id}/products`)
            }
            sx={{
              p: 2,
              borderRadius: 2,
              cursor: "pointer",
              "&:hover": {
                backgroundColor: "#2A2A2A",
              },
            }}
          >
            📦 Productos
          </Box>

          <Box
            onClick={() =>
              navigate(`/local/${id}/reports`)
            }
            sx={{
              p: 2,
              borderRadius: 2,
              cursor: "pointer",
              "&:hover": {
                backgroundColor: "#2A2A2A",
              },
            }}
          >
            📄 Reportes
          </Box>

          <Box
            onClick={() =>
              navigate(`/local/${id}/events`)
            }
            sx={{
              p: 2,
              borderRadius: 2,
              cursor: "pointer",
              "&:hover": {
                backgroundColor: "#2A2A2A",
              },
            }}
          >
            📅 Eventos
          </Box>
        </Box>
      </Box>

      <Box
        sx={{
          flex: 1,
          p: 4,
        }}
      >
        <Typography
          sx={{
            fontSize: 32,
            fontWeight: 700,
          }}
        >
          Lider Vicuña Mackenna
        </Typography>

        <Typography
          sx={{
            color: "#666",
            mt: 1,
          }}
        >
          Selecciona un módulo del menú lateral
        </Typography>
      </Box>
    </Box>
  );
}