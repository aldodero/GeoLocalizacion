import {
  Box,
  TextField,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Alert,
} from "@mui/material";

import {
  useEffect,
  useState,
} from "react";
import axios from "axios";
import AdminLayout from "../../layouts/AdminLayout";




export default function WorkersPage() {
  const [trabajadores, setTrabajadores] =
    useState<any[]>([]);

  const [busqueda, setBusqueda] =
    useState("");

  const [openModal, setOpenModal] =
    useState(false);
  const [mensajeExito, setMensajeExito] =
  useState("");

const [mensajeError, setMensajeError] =
  useState("");

  const [tiposUsuario, setTiposUsuario] =
  useState<any[]>([]);

const [tipoUsuarioId, setTipoUsuarioId] =
  useState("");

  const [nuevoTrabajador, setNuevoTrabajador] =
    useState({
      rutUsuario: "",
      primerNombre: "",
      segundoNombre: "",
      primerApellido: "",
      segundoApellido: "",
      telefono: "",
      correo: "",
      direccion: "",
      fechaNacimiento: "",
      genero: "Masculino",
      contrasena: "123456",
      
    });

  useEffect(() => {

    const cargarTiposUsuario =
  async () => {
    try {
      const response =
        await axios.get(
          "http://localhost:8082/api/tipos-usuario/listar"
        );

      setTiposUsuario(
        response.data
      );
    } catch (error) {
      console.error(
        "Error cargando cargos",
        error
      );
    }
  };

cargarTiposUsuario();
    const cargarTrabajadores =
      async () => {
        try {
          const idLocal =
            localStorage.getItem(
              "idLocal"
            );

          if (!idLocal) return;

          const asignaciones =
            await axios.get(
              `http://localhost:8083/api/Usuario-Local/Obtener-Usuario-local/${idLocal}`
            );

          const usuarios =
            await Promise.all(
              asignaciones.data.map(
                async (item: any) => {
                  try {
                    const usuario =
                      await axios.get(
                        `http://localhost:8082/api/usuarios/obtener/${item.idUsuario}`
                      );

                    return usuario.data;
                  } catch {
                    return null;
                  }
                }
              )
            );

          setTrabajadores(
            usuarios.filter(
              (u) => u !== null
            )
          );
        } catch (error) {
          console.error(
            "Error cargando trabajadores",
            error
          );
        }
      };

    cargarTrabajadores();

    window.addEventListener(
      "localChanged",
      cargarTrabajadores
    );

    return () => {
      window.removeEventListener(
        "localChanged",
        cargarTrabajadores
      );
    };
  }, []);

  const trabajadoresFiltrados =
    trabajadores.filter(
      (trabajador) =>
        trabajador.primerNombre
          ?.toLowerCase()
          .includes(
            busqueda.toLowerCase()
          ) ||
        trabajador.primerApellido
          ?.toLowerCase()
          .includes(
            busqueda.toLowerCase()
          ) ||
        trabajador.correo
          ?.toLowerCase()
          .includes(
            busqueda.toLowerCase()
          )
    );



   const guardarTrabajador =
  async () => {
    try {
      const errores: string[] = [];

      const idLocal =
        localStorage.getItem(
          "idLocal"
        );
        if (
        !nuevoTrabajador.rutUsuario ||
        !nuevoTrabajador.primerNombre ||
        !nuevoTrabajador.primerApellido ||
        !nuevoTrabajador.telefono ||
        !nuevoTrabajador.correo ||
        !nuevoTrabajador.direccion ||
        !nuevoTrabajador.fechaNacimiento ||
        !tipoUsuarioId
      ) {
        setMensajeError(
          "Debe completar todos los campos obligatorios"
        );
        

        if(
          !validarRut(
            nuevoTrabajador.rutUsuario
          )
        ){
          errores.push(
            "el RUT ingresado no es valido"
          );
        }

        if (
        nuevoTrabajador.telefono.length !== 9
      ) {
        errores.push(
          "El teléfono debe tener 9 dígitos"
        );
      }

      if (
        !nuevoTrabajador.telefono.startsWith(
          "9"
        )
      ) {
        errores.push(
          "El teléfono debe comenzar con 9"
        );
      }

      if (
    !validarCorreo(
      nuevoTrabajador.correo
    )
  ) {
    errores.push(
      "Debe ingresar un correo válido"
    );
  }
            if (
  !validarTexto(
    nuevoTrabajador.primerNombre
  )
) {
  errores.push(
    "Primer nombre inválido"
  );
}

if (
  !validarTexto(
    nuevoTrabajador.primerApellido
  )
) {
  errores.push(
    "Primer apellido inválido"
  );
}
   


if (errores.length > 0) {
  setMensajeError(
    errores.join(" | ")
  );

  setTimeout(() => {
    setMensajeError("");
  }, 6000);

  return;
}



        setTimeout(() => {
          setMensajeError("");
        }, 4000);

        return;
      }

      if (
        !idLocal ||
        !tipoUsuarioId
      ) {
        setMensajeError(
          "Debe seleccionar un cargo"
        );

        setTimeout(() => {
          setMensajeError("");
        }, 4000);

        return;
      }

      const usuarioResponse =
        await axios.post(
          
          `http://localhost:8082/api/usuarios/crear?tipoUsuarioId=${tipoUsuarioId}`,
          {
            ...nuevoTrabajador,
            telefono: Number(
              nuevoTrabajador.telefono
            ),
            fechaRegistro:
              new Date(),
            activo: true,
          }
        );

      const idUsuario =
        usuarioResponse.data
          .idUsuario;

      await axios.post(
        "http://localhost:8083/api/Usuario-Local/crear",
        {
          fechaAsignacion:
            new Date()
              .toISOString()
              .split("T")[0],

          estado: "ACTIVO",

          idUsuario,

          idLocal:
            Number(idLocal),
        }
      );

      setNuevoTrabajador({
        rutUsuario: "",
        primerNombre: "",
        segundoNombre: "",
        primerApellido: "",
        segundoApellido: "",
        telefono: "",
        correo: "",
        direccion: "",
        fechaNacimiento: "",
        genero: "Masculino",
        contrasena: "123456",
      });

      setTipoUsuarioId("");

      setOpenModal(false);

      setMensajeExito(
        "Trabajador creado correctamente"
      );

      setTimeout(() => {
        setMensajeExito("");
      }, 4000);

      window.dispatchEvent(
        new Event(
          "localChanged"
        )
      );

    } catch (error) {
      console.error(error);

      setMensajeError(
        "Error creando trabajador"
      );

      setTimeout(() => {
        setMensajeError("");
      }, 4000);
    }
  };


        const formatearRut = (
      valor: string
    ) => {
      const limpio = valor.replace(
        /[^0-9kK]/g,
        ""
      );

      if (limpio.length <= 1) {
        return limpio;
      }

      const dv =
        limpio.substring(
          limpio.length - 1
        );

      const numero =
        limpio.substring(
          0,
          limpio.length - 1
        );

      const numeroFormateado =
        numero.replace(
          /\B(?=(\d{3})+(?!\d))/g,
          "."
        );

      return (
        numeroFormateado +
        "-" +
        dv.toUpperCase()
      );
    };

    const validarRut = (
  rut: string
) => {
  const limpio = rut
    .replace(/\./g, "")
    .replace("-", "");

  if (limpio.length < 8) {
    return false;
  }

  const dv = limpio
    .slice(-1)
    .toUpperCase();

  const numero = parseInt(
    limpio.slice(0, -1)
  );

  let suma = 0;
  let multiplo = 2;
  let num = numero;

  while (num > 0) {
    suma +=
      (num % 10) * multiplo;

    num = Math.floor(
      num / 10
    );

    multiplo++;

    if (multiplo > 7) {
      multiplo = 2;
    }
  }

  const resto =
    11 - (suma % 11);

  let dvEsperado = "";

  if (resto === 11)
    dvEsperado = "0";
  else if (resto === 10)
    dvEsperado = "K";
  else
    dvEsperado =
      resto.toString();

  return dv === dvEsperado;
};



const validarTexto = (
  texto: string
) => {
  return /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+$/.test(
    texto
  );
};

const validarCorreo = (
  correo: string
) => {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(
    correo
  );
};

  return (
    <AdminLayout>
       {mensajeExito && (
            <Alert
              severity="success"
              sx={{ mb: 2 }}
            >
              {mensajeExito}
            </Alert>
          )}

      <Box
        sx={{
          display: "flex",
          justifyContent: "flex-end",
          mb: 2,
        }}
      >
        
        
        <Button
          variant="contained"
          onClick={() =>
            setOpenModal(true)
          }
          sx={{
            backgroundColor:
              "#FF9800",
            fontWeight: 700,
            "&:hover": {
              backgroundColor:
                "#F57C00",
            },
          }}
        >
          + Nuevo trabajador
        </Button>
      </Box>
          
      <TextField
        fullWidth
        placeholder="Buscar trabajador..."
        value={busqueda}
        onChange={(e) =>
          setBusqueda(
            e.target.value
          )
        }
        sx={{
          mb: 3,
        }}
      />

      <Box
        sx={{
          backgroundColor:
            "#FFFFFF",
          borderRadius: 4,
          p: 3,
        }}
      >
        <Box
          sx={{
            display: "grid",
            gridTemplateColumns:
              "2fr 2fr 2fr 2fr 1fr",
            gap: 2,
            pb: 2,
            borderBottom:
              "1px solid #E0E0E0",
            fontWeight: 700,
          }}
        >
          <div>RUT</div>
          <div>Nombre</div>
          <div>Correo</div>
          <div>Cargo</div>
          <div>Estado</div>
        </Box>

        {trabajadoresFiltrados.map(
          (trabajador) => (
            <Box
              key={
                trabajador.idUsuario
              }
              sx={{
                display: "grid",
                gridTemplateColumns:
                  "2fr 2fr 2fr 2fr 1fr",
                gap: 2,
                py: 2,
                borderBottom:
                  "1px solid #F0F0F0",
              }}
            >
              <div>
                {
                  trabajador.rutUsuario
                }
              </div>

              <div>
                {
                  trabajador.primerNombre
                }{" "}
                {
                  trabajador.primerApellido
                }
              </div>

              <div>
                {
                  trabajador.correo
                }
              </div>

              <div>
                {
                  trabajador.tipoUsuario[
                    "NombreTipoUsuario"
                  ]
                }
              </div>

              <div>
                {trabajador.activo
                  ? "Activo"
                  : "Inactivo"}
              </div>
            </Box>
          )
        )}
      </Box>

      <Dialog
        open={openModal}
        onClose={() =>
          setOpenModal(false)
        }
        maxWidth="md"
        fullWidth
      >
        <DialogTitle>
          Nuevo trabajador
        </DialogTitle>




        <DialogContent>
          {mensajeError && (
          <Alert
            severity="error"
            sx={{ mb: 2 }}
          >
            {mensajeError}
          </Alert>
        )}
        <Box
          sx={{
            display: "grid",
            gridTemplateColumns:
              "1fr 1fr",
            gap: 2,
            mt: 1,
          }}
        >
         <TextField
            label="RUT"
            placeholder="12.345.678-9"
            value={
              nuevoTrabajador.rutUsuario
            }
            onChange={(e) =>
              setNuevoTrabajador({
                ...nuevoTrabajador,
                rutUsuario:
                  formatearRut(
                    e.target.value
                  ),
              })
            }
            fullWidth
          />



          <TextField
            label="Teléfono"
            value={
              nuevoTrabajador.telefono
            }
            onChange={(e) =>
              setNuevoTrabajador({
                ...nuevoTrabajador,
                telefono:
                  e.target.value.replace(
                    /\D/g,
                    ""
                  )
                  .slice(0, 9)
              })
            }
            fullWidth
          />



          <TextField
            label="Primer nombre"
            value={
              nuevoTrabajador.primerNombre
            }
            onChange={(e) =>
              setNuevoTrabajador({
                ...nuevoTrabajador,
                primerNombre:
                  e.target.value,
              })
            }
            fullWidth
          />

          <TextField
            label="Segundo nombre"
            value={
              nuevoTrabajador.segundoNombre
            }
            onChange={(e) =>
              setNuevoTrabajador({
                ...nuevoTrabajador,
                segundoNombre:
                  e.target.value,
              })
            }
            fullWidth
          />

          <TextField
            label="Primer apellido"
            value={
              nuevoTrabajador.primerApellido
            }
            onChange={(e) =>
              setNuevoTrabajador({
                ...nuevoTrabajador,
                primerApellido:
                  e.target.value,
              })
            }
            fullWidth
          />

          <TextField
            label="Segundo apellido"
            value={
              nuevoTrabajador.segundoApellido
            }
            onChange={(e) =>
              setNuevoTrabajador({
                ...nuevoTrabajador,
                segundoApellido:
                  e.target.value,
              })
            }
            fullWidth
          />

          <TextField
            label="Correo"
            value={
              nuevoTrabajador.correo
            }
            onChange={(e) =>
              setNuevoTrabajador({
                ...nuevoTrabajador,
                correo:
                  e.target.value,
              })
            }
            fullWidth
          />

          <TextField
            label="Dirección"
            value={
              nuevoTrabajador.direccion
            }
            onChange={(e) =>
              setNuevoTrabajador({
                ...nuevoTrabajador,
                direccion:
                  e.target.value,
              })
            }
            fullWidth
          />

          <TextField
          label="Fecha nacimiento"
          type="date"
          value={nuevoTrabajador.fechaNacimiento}
          onChange={(e) =>
            setNuevoTrabajador({
              ...nuevoTrabajador,
              fechaNacimiento:
                e.target.value,
            })
          }
          slotProps={{
            inputLabel: {
              shrink: true,
            },
          }}
          fullWidth
        />

          <FormControl fullWidth>
            <InputLabel>
              Género
            </InputLabel>

            <Select
              value={
                nuevoTrabajador.genero
              }
              label="Género"
              onChange={(e) =>
                setNuevoTrabajador({
                  ...nuevoTrabajador,
                  genero: String(
                    e.target.value
                  ),
                })
              }
            >
              <MenuItem value="Masculino">
                Masculino
              </MenuItem>

              <MenuItem value="Femenino">
                Femenino
              </MenuItem>
            </Select>
          </FormControl>

          <FormControl fullWidth>
            <InputLabel>
              Cargo
            </InputLabel>

            <Select
              value={tipoUsuarioId}
              label="Cargo"
              onChange={(e) =>
                setTipoUsuarioId(
                  String(
                    e.target.value
                  )
                )
              }
            >
              {tiposUsuario.map(
                (tipo: any) => (
                  <MenuItem
                    key={
                      tipo.idTipoUsuario
                    }
                    value={
                      tipo.idTipoUsuario
                    }
                  >
                    {
                      tipo.NombreTipoUsuario
                    }
                  </MenuItem>
                )
              )}
            </Select>
          </FormControl>
        </Box>
      </DialogContent>




        <DialogActions>
          <Button
            onClick={() =>
              setOpenModal(false)
            }
          >
            Cancelar
          </Button>

            
            <Button
            variant="contained"
            onClick={
              guardarTrabajador
            }
            sx={{
              backgroundColor:
                "#FF9800",
            }}
          >
            Guardar
          </Button>
          </DialogActions>
          
      </Dialog>

    </AdminLayout>
  );
}