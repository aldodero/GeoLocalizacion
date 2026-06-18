import {
  Box,
  Paper,
  Typography,
  TextField,
  Button,
} from "@mui/material";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import { useNavigate } from "react-router-dom";
import { useState } from "react";


export default function LoginPage() {
  const navigate = useNavigate();

  const [correo, setCorreo] = useState("");
  const [contrasena, setContrasena] = useState("");

    const handleLogin = () => {
    localStorage.setItem("token", "test-token");
    localStorage.setItem("idUsuario", "1");
    localStorage.setItem("correo", "admin@test.com");
    localStorage.setItem("tipoUsuario", "ADMIN");

    navigate("/workers");
  };

  return (
    <Box
      sx={{
        minHeight: "100vh",
        bgcolor: "#F5F5F5",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        px: 2,
      }}
    >
      <Paper
        elevation={0}
        sx={{
          width: "100%",
          maxWidth: 520,
          p: 5,
          borderRadius: 5,
          bgcolor: "#FFFFFF",
          boxShadow: "0 10px 40px rgba(0,0,0,0.08)",
        }}
      >
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            mb: 4,
          }}
        >
          <Box
            sx={{
              width: 72,
              height: 72,
              bgcolor: "#111",
              borderRadius: 3,
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              mb: 2,
            }}
          >
            <LocationOnIcon
              sx={{
                color: "#FF9800",
                fontSize: 40,
              }}
            />
          </Box>

          <Typography
            sx={{
              fontSize: 30,
              fontWeight: 600,
              textAlign: "center",
            }}
          >
            Administración GeoMarket
          </Typography>

          <Typography
            sx={{
              fontSize: 16,
              color: "#666",
              textAlign: "center",
              mt: 1,
            }}
          >
            Ingresa tus datos para continuar
          </Typography>
        </Box>

        <TextField
          label="Correo"
          fullWidth
          margin="normal"
          value={correo}
          onChange={(e) =>
            setCorreo(e.target.value)
          }
        />

        <TextField
          label="Contraseña"
          type="password"
          fullWidth
          margin="normal"
          value={contrasena}
          onChange={(e) =>
            setContrasena(e.target.value)
          }
        />

        <Button
          fullWidth
          variant="contained"
          size="large"
          onClick={handleLogin}
          sx={{
            mt: 4,
            height: 52,
            backgroundColor: "#FF9800",
            fontWeight: 700,
            borderRadius: 3,
            boxShadow: "none",
            "&:hover": {
              backgroundColor: "#F57C00",
              boxShadow: "none",
            },
          }}
        >
          Acceder
        </Button>
      </Paper>
    </Box>
  );
}