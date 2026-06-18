import {
  Box,
  Typography,
  Avatar,
  Menu,
  MenuItem,
} from "@mui/material";

import {
  useNavigate,
  useLocation,
} from "react-router-dom";

import {
  useState,
  useEffect,
} from "react";

import axios from "axios";

interface Props {
  children: React.ReactNode;
}

export default function AdminLayout({
  children,
}: Props) {
  const navigate = useNavigate();
  const location = useLocation();

  const [locals, setLocals] = useState<any[]>([]);
  const [local, setLocal] = useState<any>(null);

  const [anchorEl, setAnchorEl] =
    useState<null | HTMLElement>(null);

  useEffect(() => {
    const cargarLocales = async () => {
      try {
        const idUsuario =
          localStorage.getItem("idUsuario");

        if (!idUsuario) return;


        const response = await axios.get(
          `http://localhost:8083/api/Usuario-Local/Obtener-usuario/${idUsuario}`
        );

        setLocals(response.data);



        if (response.data.length > 0) {
         const idLocalGuardado = localStorage.getItem("idLocal");
         const localSeleccionado = 
          response.data.find(
            (x: any) =>String(x.idLocal)==idLocalGuardado
          );

          if(localSeleccionado){
            setLocal(localSeleccionado);
          } else{
            setLocal(response.data[0]);

            localStorage.setItem(
              "idLocal",
              String(response.data[0].idLocal)
            );
          }
        }


      } catch (error) {
        console.error(
          "Error cargando locales",
          error
        );
      }
    };

    cargarLocales();
  }, []);

  const isActive = (module: string) =>
    location.pathname.includes(module);

  const menuStyle = (active: boolean) => ({
    p: 2,
    borderRadius: 3,
    cursor: "pointer",
    fontWeight: active ? 700 : 500,
    backgroundColor: active
      ? "#FF9800"
      : "transparent",
    color: "#FFFFFF",
    transition: "0.2s",
    "&:hover": {
      backgroundColor: active
        ? "#FF9800"
        : "#2A2A2A",
    },
  });

  return (
    <Box
      sx={{
        display: "flex",
        minHeight: "100vh",
        backgroundColor: "#F5F5F5",
      }}
    >
      {/* SIDEBAR */}
      <Box
        sx={{
          width: 260,
          backgroundColor: "#1E1E1E",
          color: "#FFF",
          display: "flex",
          flexDirection: "column",
        }}
      >
        <Box sx={{ p: 3 }}>
          <Typography
            sx={{
              color: "#FF9800",
              fontSize: 38,
              fontWeight: 700,
            }}
          >
            GeoMarket
          </Typography>
        </Box>

        <Box
          sx={{
            px: 2,
            display: "flex",
            flexDirection: "column",
            gap: 1,
          }}
        >
          <Box
            onClick={() => navigate("/workers")}
            sx={menuStyle(isActive("workers"))}
          >
            Trabajadores
          </Box>

          <Box
            onClick={() => navigate("/products")}
            sx={menuStyle(isActive("products"))}
          >
            Productos
          </Box>

          <Box
            onClick={() => navigate("/reports")}
            sx={menuStyle(isActive("reports"))}
          >
            Reportes
          </Box>

          <Box
            onClick={() => navigate("/events")}
            sx={menuStyle(isActive("events"))}
          >
            Eventos
          </Box>
        </Box>

        <Box sx={{ flex: 1 }} />

        <Box
          onClick={() => {
            localStorage.clear();
            navigate("/login");
          }}
          sx={{
            mx: 2,
            mb: 3,
            p: 2,
            borderRadius: 3,
            cursor: "pointer",
            color: "#FF6B6B",
            fontWeight: 600,
            "&:hover": {
              backgroundColor: "#2A2A2A",
            },
          }}
        >
          Cerrar sesión
        </Box>
      </Box>

      {/* CONTENIDO */}
      <Box
        sx={{
          flex: 1,
          display: "flex",
          flexDirection: "column",
        }}
      >
        {/* HEADER */}
        <Box
          sx={{
            height: 72,
            backgroundColor: "#FFFFFF",
            borderBottom: "1px solid #E5E5E5",
            display: "flex",
            alignItems: "center",
            px: 3,
          }}
        >
          {/* LOCAL */}
          <Box
            sx={{
              flex: 1,
              display: "flex",
              alignItems: "center",
            }}
          >
            <Typography
              sx={{
                color: "#777",
                fontSize: 24,
                mr: 1,
              }}
            >
              Local activo:
            </Typography>

            <Typography
              sx={{
                fontSize: 24,
                fontWeight: 700,
              }}
            >
              {local?.nombreLocal ||
                "Cargando..."}
            </Typography>

            <Box
              onClick={(e) =>
                setAnchorEl(
                  e.currentTarget
                )
              }
              sx={{
                ml: 1,
                display: "flex",
                alignItems: "center",
                cursor: "pointer",
              }}
            >
              <Typography
                sx={{
                  color: "#FF9800",
                  fontWeight: 700,
                }}
              >
                ▼
              </Typography>
            </Box>

            <Box
              sx={{
                flex: 1,
                height: 1,
                backgroundColor: "#E5E5E5",
                ml: 3,
              }}
            />
          </Box>

          {/* USUARIO */}
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
          </Box>
        </Box>

        <Menu
          anchorEl={anchorEl}
          open={Boolean(anchorEl)}
          onClose={() =>
            setAnchorEl(null)
          }
          anchorOrigin={{
            vertical: "bottom",
            horizontal: "center",
          }}
          transformOrigin={{
            vertical: "top",
            horizontal: "center",
          }}
        >
          {locals.map(
            (item: any, index: number) => (
              <MenuItem
                key={index}

                onClick={() => {
                  setLocal(item);
                  localStorage.setItem(
                    "idLocal",
                    String(item.idLocal)
                  )

                  setAnchorEl(null);
                  window.dispatchEvent(
                    new Event("localChanged")
                  );
                }}

              
              >
                {item.nombreLocal}
              </MenuItem>
            )
          )}
        </Menu>

        <Box
          sx={{
            flex: 1,
            p: 4,
          }}
        >
          {children}
        </Box>
      </Box>
    </Box>
  );
}