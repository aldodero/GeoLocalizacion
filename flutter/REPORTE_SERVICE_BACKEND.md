# Reporte-Service Backend - Sistema de Incidencias Operacionales

## 🎯 Concepto
Sistema de tickets/incidencias para que trabajadores y reponedores reporten problemas operacionales en GeoMarket.

**NO ES UN SISTEMA DE ANALYTICS/DASHBOARD**

---

## 🔧 Configuración

**Puerto:** `8086`  
**Base URL:** `http://10.0.2.2:8086`

---

## 📊 Modelo de Datos

### Entidad: Reporte

```java
@Entity
@Table(name = "reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReporte;
    
    private Long idUsuario;
    private Long idLocal;
    private String tipoReporte;
    private String descripcion;
    private String estado;
    private String prioridad;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;
}
```

### Tipos de Reporte (tipoReporte)
- `producto_no_encontrado`
- `ubicacion_incorrecta`
- `gondola_equivocada`
- `producto_vencido`
- `stock_incorrecto`
- `codigo_danado`
- `mapa_incorrecto`
- `otro`

### Estados (estado)
- `PENDIENTE` - Estado inicial
- `EN_REVISION` - Siendo revisado por admin
- `RESUELTO` - Problema solucionado
- `RECHAZADO` - Reporte inválido

### Prioridades (prioridad)
- `BAJA`
- `MEDIA`
- `ALTA`

---

## 🔌 Endpoints

### 1. Crear Reporte
```
POST /api/reportes/crear
```

**Request Body:**
```json
{
  "idUsuario": 1,
  "idLocal": 1,
  "tipoReporte": "producto_no_encontrado",
  "descripcion": "El producto Leche Entera no está en el pasillo 3 como indica el mapa",
  "prioridad": "MEDIA",
  "estado": "PENDIENTE"
}
```

**Response (201 Created):**
```json
{
  "idReporte": 1,
  "mensaje": "Reporte creado correctamente"
}
```

---

### 2. Obtener Reportes del Usuario
```
GET /api/reportes/usuario/{idUsuario}
```

**Response (200 OK):**
```json
[
  {
    "idReporte": 1,
    "idUsuario": 1,
    "idLocal": 1,
    "tipoReporte": "producto_no_encontrado",
    "descripcion": "El producto Leche Entera no está en el pasillo 3",
    "estado": "PENDIENTE",
    "prioridad": "MEDIA",
    "fechaCreacion": "2024-01-15T10:30:00",
    "fechaResolucion": null
  },
  {
    "idReporte": 2,
    "idUsuario": 1,
    "idLocal": 1,
    "tipoReporte": "ubicacion_incorrecta",
    "descripcion": "El mapa muestra ubicación incorrecta del producto",
    "estado": "RESUELTO",
    "prioridad": "ALTA",
    "fechaCreacion": "2024-01-14T15:20:00",
    "fechaResolucion": "2024-01-15T09:00:00"
  }
]
```

---

### 3. Obtener Detalle de Reporte
```
GET /api/reportes/{idReporte}
```

**Response (200 OK):**
```json
{
  "idReporte": 1,
  "idUsuario": 1,
  "idLocal": 1,
  "tipoReporte": "producto_no_encontrado",
  "descripcion": "El producto Leche Entera no está en el pasillo 3 como indica el mapa",
  "estado": "PENDIENTE",
  "prioridad": "MEDIA",
  "fechaCreacion": "2024-01-15T10:30:00",
  "fechaResolucion": null
}
```

---

### 4. Actualizar Estado (Admin)
```
PUT /api/reportes/estado/{idReporte}
```

**Request Body:**
```json
{
  "estado": "RESUELTO"
}
```

**Response (200 OK):**
```json
{
  "mensaje": "Estado actualizado correctamente"
}
```

---

## 🔄 Flujo de Trabajo

### Usuario/Reponedor:
1. Encuentra un problema operacional
2. Abre la app → Menú perfil → Reportes
3. Presiona "Crear reporte"
4. Selecciona tipo de problema
5. Describe el problema
6. Selecciona prioridad
7. Envía el reporte
8. Puede consultar estado en cualquier momento

### Administrador (Futuro):
1. Recibe notificación de nuevo reporte
2. Revisa el reporte
3. Cambia estado a "EN_REVISION"
4. Soluciona el problema
5. Marca como "RESUELTO"

---

## 📱 Integración Frontend

### Service Flutter
```dart
class ReporteService {
  final String baseUrl = "http://10.0.2.2:8086";
  
  Future<bool> crearReporte(Map<String, dynamic> data);
  Future<List<dynamic>> obtenerReportesUsuario(int idUsuario);
  Future<Map<String, dynamic>?> obtenerDetalleReporte(int idReporte);
  Future<bool> actualizarEstadoReporte(int idReporte, String nuevoEstado);
}
```

---

## 🎨 UI/UX

### Pantalla Principal
- Lista de reportes del usuario
- Botón flotante "Crear reporte"
- Cards con estado visual (colores)
- Pull to refresh
- Empty state amigable

### Formulario Crear Reporte
- Dropdown tipo de problema (8 opciones)
- Chips de prioridad (BAJA, MEDIA, ALTA)
- TextField descripción (multiline)
- Botón "Enviar reporte"
- Loading state

### Estados Visuales
- 🟠 PENDIENTE - Naranja
- 🔵 EN_REVISION - Azul
- 🟢 RESUELTO - Verde
- 🔴 RECHAZADO - Rojo

---

## 🔐 Seguridad (Futuro)

- Validar que el usuario solo pueda ver sus propios reportes
- Solo administradores pueden cambiar estados
- Rate limiting para evitar spam de reportes
- Validación de campos obligatorios

---

## 📊 Métricas Sugeridas (Futuro)

- Total de reportes por tipo
- Tiempo promedio de resolución
- Reportes por local
- Reportes por usuario
- Tendencias de problemas

---

## ✅ Checklist de Implementación

### Backend
- [ ] Crear entidad Reporte
- [ ] Crear ReporteRepository
- [ ] Crear ReporteService
- [ ] Crear ReporteController
- [ ] Implementar POST /crear
- [ ] Implementar GET /usuario/{id}
- [ ] Implementar GET /{id}
- [ ] Implementar PUT /estado/{id}
- [ ] Validaciones de datos
- [ ] Manejo de errores

### Frontend (✅ Completado)
- [x] Crear ReporteService
- [x] Crear ReportsPage
- [x] Implementar listado de reportes
- [x] Implementar formulario crear reporte
- [x] Estados visuales por color
- [x] Empty state
- [x] Loading states
- [x] Pull to refresh
- [x] Validaciones de formulario

---

## 🚀 Próximos Pasos

1. Implementar backend en Spring Boot
2. Probar endpoints con Postman
3. Conectar Flutter con backend real
4. Agregar notificaciones push para administradores
5. Panel de administración para gestionar reportes
6. Estadísticas y métricas de reportes
