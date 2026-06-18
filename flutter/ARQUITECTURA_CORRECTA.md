# Arquitectura Correcta - GeoMarket

## ✅ SERVICIOS Y SUS RESPONSABILIDADES

### 1. Historial-Service (Puerto 8084)
**Responsabilidad:** Actividad de búsqueda del usuario
- Guardar búsquedas realizadas
- Listar historial de búsquedas
- Tracking de productos consultados

**Endpoints:**
- `POST /api/historial/guardar`
- `GET /api/historial/listar`

---

### 2. Favoritos-Service (Puerto 8085)
**Responsabilidad:** Productos favoritos del usuario
- Guardar productos favoritos
- Listar favoritos
- Eliminar favoritos
- Verificar si un producto es favorito

**Endpoints:**
- `POST /api/favoritos/guardar`
- `GET /api/favoritos/listar`
- `DELETE /api/favoritos/eliminar`

---

### 3. Reporte-Service (Puerto 8086) 🚨
**Responsabilidad:** Sistema de incidencias operacionales

**NO ES UN SISTEMA DE ANALYTICS/DASHBOARD**

Este servicio es para que trabajadores/reponedores reporten problemas:
- ❌ Producto no encontrado
- ❌ Ubicación incorrecta
- ❌ Góndola errónea
- ❌ Producto vencido
- ❌ Stock incorrecto
- ❌ Código de barras dañado

**Endpoints futuros:**
- `POST /api/reportes/crear` - Crear incidencia
- `GET /api/reportes/usuario/{id}` - Ver mis reportes
- `GET /api/reportes/admin` - Ver todos los reportes (admin)
- `PUT /api/reportes/{id}/resolver` - Marcar como resuelto

**Estructura de un reporte:**
```json
{
  "idReporte": 1,
  "idUsuario": 1,
  "idProducto": 123,
  "tipo": "ubicacion_incorrecta",
  "descripcion": "El producto está en pasillo 3, no en pasillo 5",
  "estado": "pendiente",
  "fechaCreacion": "2024-01-15T10:30:00",
  "fechaResolucion": null
}
```

---

## ✅ PANTALLAS Y SUS FUENTES DE DATOS

### ActivityPage (Mi actividad)
**Consume:**
- ✅ Historial-Service → búsquedas recientes
- ✅ Favoritos-Service → favoritos guardados

**Muestra:**
- Cantidad de búsquedas
- Cantidad de favoritos
- Últimas 5 búsquedas
- Últimos 3 favoritos

---

### ReportsPage (Reportes) - FUTURO
**Consume:**
- ✅ Reporte-Service → incidencias operacionales

**Muestra:**
- Crear nuevo reporte de incidencia
- Ver mis reportes enviados
- Estado de resolución
- Historial de incidencias

---

## 🎯 SEPARACIÓN CLARA

| Concepto | Servicio | Pantalla |
|----------|----------|----------|
| 📊 Actividad del usuario | Historial + Favoritos | ActivityPage |
| 🚨 Incidencias operacionales | Reporte-Service | ReportsPage |

---

## ⚠️ IMPORTANTE

**ActivityPage NO debe depender de Reporte-Service**

La confusión inicial fue asumir que "reportes" = "analytics/estadísticas"

Pero en GeoMarket:
- **Reportes** = Sistema de tickets/incidencias
- **Actividad** = Historial + Favoritos del usuario
