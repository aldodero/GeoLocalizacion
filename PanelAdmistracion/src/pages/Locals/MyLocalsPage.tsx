import {
  Box,
  Card,
  Button,
  Typography,
  Avatar,
} from "@mui/material";
import { useNavigate } from "react-router-dom";

const locals = [
  {
    id: "1",
    name: "Lider Vicuña Mackenna",
    address: "Av. Vicuña Mackenna 1234",
  },
  {
    id: "2",
    name: "Local Centro",
    address: "Paseo Ahumada 500",
  },
];

export default function MyLocalsPage() {
  const navigate = useNavigate();

  return (
    <Box
      sx={{
        minHeight: "100vh",
        backgroundColor: "#F5F5F5",
      }}
    >
      {/* Header */}
      <Box
        sx={{
          height: 70,
          backgroundColor: "#FFFFFF",
          borderBottom: "1px solid #E0E0E0",
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
          px: 4,
        }}
      >
        <Typography
          sx={{
            fontSize: 24,
            fontWeight: 700,
            color: "#FF9800",
          }}
        >
          GeoMarket
        </Typography>

        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            gap: 2,
          }}
        >
          <Typography>
            Supervisor
          </Typography>

          <Avatar
            sx={{
              bgcolor: "#FF9800",
            }}
          >
            S
          </Avatar>

          <Button
            variant="outlined"
            onClick={() => navigate("/login")}
          >
            Salir
          </Button>
        </Box>
      </Box>

      {/* Contenido */}
      <Box sx={{ p: 5 }}>
        <Typography
          sx={{
            fontSize: 32,
            fontWeight: 700,
            mb: 1,
          }}
        >
          Mis Locales
        </Typography>

        <Typography
          sx={{
            color: "#666",
            mb: 4,
          }}
        >
          Selecciona el local que deseas administrar
        </Typography>

        <Box
          sx={{
            display: "flex",
            gap: 3,
            flexWrap: "wrap",
          }}
        >
          {locals.map((local) => (
            <Card
              key={local.id}
              sx={{
                width: 360,
                p: 3,
                borderRadius: 4,
                boxShadow:
                  "0 4px 15px rgba(0,0,0,0.08)",
              }}
            >
              <Typography
                sx={{
                  fontSize: 22,
                  fontWeight: 700,
                  mb: 1,
                }}
              >
                {local.name}
              </Typography>

              <Typography
                sx={{
                  color: "#666",
                  mb: 3,
                }}
              >
                {local.address}
              </Typography>

              <Button
                fullWidth
                variant="contained"
                sx={{
                  backgroundColor: "#FF9800",
                }}
                onClick={() =>
                  navigate(`/local/${local.id}`)
                }
              >
                Administrar Local
              </Button>
            </Card>
          ))}
        </Box>
      </Box>
    </Box>
  );
}