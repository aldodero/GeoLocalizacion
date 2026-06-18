import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../../../service/notificacion_service.dart';
import 'notification_detail_page.dart';

enum FiltroNotificacion {
  todas,
  noLeidas,
  leidas,
  favorito,
  escaneo,
  admin,
  evento,
  reporte,
}

class NotificationPage extends StatefulWidget {
  final int idLocal;
  
  const NotificationPage({
    super.key,
    required this.idLocal,
  });

  @override
  State<NotificationPage> createState() => _NotificationPageState();
}

class _NotificationPageState extends State<NotificationPage> {
  final NotificacionService notificacionService = NotificacionService();
  List<dynamic> todasNotificaciones = [];
  List<dynamic> notificacionesFiltradas = [];
  bool cargando = true;
  FiltroNotificacion filtroActivo = FiltroNotificacion.todas;

  @override
  void initState() {
    super.initState();
    cargarNotificaciones();
  }

  Future<void> cargarNotificaciones() async {
    try {
      final data = await notificacionService.obtenerNotificaciones(1, widget.idLocal);
      print("DEBUG NOTIFICACIONES RECIBIDAS:");
      print(data);
      setState(() {
        todasNotificaciones = data;
        aplicarFiltro();
      });
    } catch (e) {
      print("ERROR PAGE NOTIFICACIONES: $e");
    } finally {
      setState(() {
        cargando = false;
      });
    }
  }

  void aplicarFiltro() {
    switch (filtroActivo) {
      case FiltroNotificacion.todas:
        notificacionesFiltradas = List.from(todasNotificaciones);
        break;
      case FiltroNotificacion.noLeidas:
        notificacionesFiltradas = todasNotificaciones
            .where((n) => n["estadoNotificacion"] == "NO_LEIDA")
            .toList();
        break;
      case FiltroNotificacion.leidas:
        notificacionesFiltradas = todasNotificaciones
            .where((n) => n["estadoNotificacion"] == "LEIDA")
            .toList();
        break;
      case FiltroNotificacion.favorito:
        notificacionesFiltradas = todasNotificaciones
            .where((n) => n["tipoNotificacion"]?["nombreNotificacion"] == "FAVORITO")
            .toList();
        break;
      case FiltroNotificacion.escaneo:
        notificacionesFiltradas = todasNotificaciones
            .where((n) => n["tipoNotificacion"]?["nombreNotificacion"] == "ESCANEO")
            .toList();
        break;
      case FiltroNotificacion.admin:
        notificacionesFiltradas = todasNotificaciones
            .where((n) => n["tipoNotificacion"]?["nombreNotificacion"] == "ADMIN")
            .toList();
        break;
      case FiltroNotificacion.evento:
        notificacionesFiltradas = todasNotificaciones
            .where((n) => n["tipoNotificacion"]?["nombreNotificacion"] == "EVENTO")
            .toList();
        break;
      case FiltroNotificacion.reporte:
        notificacionesFiltradas = todasNotificaciones
            .where((n) => n["tipoNotificacion"]?["nombreNotificacion"] == "REPORTE")
            .toList();
        break;
    }
  }

  Map<String, List<dynamic>> agruparPorFecha(List<dynamic> notificaciones) {
    final Map<String, List<dynamic>> grupos = {};
    final ahora = DateTime.now();
    final hoy = DateTime(ahora.year, ahora.month, ahora.day);
    final ayer = hoy.subtract(const Duration(days: 1));
    final semanaAtras = hoy.subtract(const Duration(days: 7));

    for (final notificacion in notificaciones) {
      try {
        final fechaStr = notificacion["fechaNotificacion"] ?? "";
        if (fechaStr.isEmpty) continue;

        final fecha = DateTime.parse(fechaStr);
        final fechaSinHora = DateTime(fecha.year, fecha.month, fecha.day);

        String categoria;
        if (fechaSinHora == hoy) {
          categoria = "Hoy";
        } else if (fechaSinHora == ayer) {
          categoria = "Ayer";
        } else if (fechaSinHora.isAfter(semanaAtras)) {
          categoria = "Esta semana";
        } else {
          categoria = "Anteriores";
        }

        grupos.putIfAbsent(categoria, () => []);
        grupos[categoria]!.add(notificacion);
      } catch (e) {
        print("Error procesando fecha: $e");
      }
    }

    // Ordenar las notificaciones dentro de cada grupo por hora (más recientes primero)
    grupos.forEach((key, value) {
      value.sort((a, b) {
        try {
          final horaA = DateTime.parse(a["horaNotificacion"] ?? "");
          final horaB = DateTime.parse(b["horaNotificacion"] ?? "");
          return horaB.compareTo(horaA);
        } catch (e) {
          return 0;
        }
      });
    });

    return grupos;
  }

  String getNombreFiltro(FiltroNotificacion filtro) {
    switch (filtro) {
      case FiltroNotificacion.todas:
        return "Todas";
      case FiltroNotificacion.noLeidas:
        return "No leídas";
      case FiltroNotificacion.leidas:
        return "Leídas";
      case FiltroNotificacion.favorito:
        return "Favoritos";
      case FiltroNotificacion.escaneo:
        return "Escaneos";
      case FiltroNotificacion.admin:
        return "Administración";
      case FiltroNotificacion.evento:
        return "Eventos";
      case FiltroNotificacion.reporte:
        return "Reportes";
    }
  }

  void mostrarFiltros() {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
      ),
      builder: (context) => DraggableScrollableSheet(
        initialChildSize: 0.6,
        minChildSize: 0.4,
        maxChildSize: 0.8,
        expand: false,
        builder: (context, scrollController) => Container(
          padding: const EdgeInsets.all(20),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'Filtrar notificaciones',
                style: Theme.of(context).textTheme.titleLarge?.copyWith(
                  fontWeight: FontWeight.w600,
                ),
              ),
              const SizedBox(height: 20),
              Expanded(
                child: ListView(
                  controller: scrollController,
                  children: FiltroNotificacion.values.map((filtro) {
                    final isSelected = filtroActivo == filtro;
                    final count = _getConteoFiltro(filtro);
                    
                    return ListTile(
                      leading: Icon(
                        isSelected ? Icons.radio_button_checked : Icons.radio_button_unchecked,
                        color: isSelected ? AppColors.orange : AppColors.mediumGrey,
                      ),
                      title: Text(getNombreFiltro(filtro)),
                      trailing: Container(
                        padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                        decoration: BoxDecoration(
                          color: AppColors.orange.withOpacity(0.1),
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: Text(
                          count.toString(),
                          style: const TextStyle(
                            color: AppColors.orange,
                            fontWeight: FontWeight.w600,
                            fontSize: 12,
                          ),
                        ),
                      ),
                      onTap: () {
                        setState(() {
                          filtroActivo = filtro;
                          aplicarFiltro();
                        });
                        Navigator.pop(context);
                      },
                    );
                  }).toList(),
                ),
              ),
              const SizedBox(height: 20),
            ],
          ),
        ),
      ),
    );
  }

  int _getConteoFiltro(FiltroNotificacion filtro) {
    switch (filtro) {
      case FiltroNotificacion.todas:
        return todasNotificaciones.length;
      case FiltroNotificacion.noLeidas:
        return todasNotificaciones.where((n) => n["estadoNotificacion"] == "NO_LEIDA").length;
      case FiltroNotificacion.leidas:
        return todasNotificaciones.where((n) => n["estadoNotificacion"] == "LEIDA").length;
      case FiltroNotificacion.favorito:
        return todasNotificaciones.where((n) => n["tipoNotificacion"]?["nombreNotificacion"] == "FAVORITO").length;
      case FiltroNotificacion.escaneo:
        return todasNotificaciones.where((n) => n["tipoNotificacion"]?["nombreNotificacion"] == "ESCANEO").length;
      case FiltroNotificacion.admin:
        return todasNotificaciones.where((n) => n["tipoNotificacion"]?["nombreNotificacion"] == "ADMIN").length;
      case FiltroNotificacion.evento:
        return todasNotificaciones.where((n) => n["tipoNotificacion"]?["nombreNotificacion"] == "EVENTO").length;
      case FiltroNotificacion.reporte:
        return todasNotificaciones.where((n) => n["tipoNotificacion"]?["nombreNotificacion"] == "REPORTE").length;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        backgroundColor: AppColors.white,
        elevation: 0,
        iconTheme: const IconThemeData(color: AppColors.black),
        title: const Text(
          'Notificaciones',
          style: TextStyle(
            color: AppColors.black,
            fontSize: 20,
            fontWeight: FontWeight.w600,
          ),
        ),
        actions: [
          Stack(
            children: [
              IconButton(
                icon: const Icon(Icons.tune, color: AppColors.black),
                onPressed: mostrarFiltros,
              ),
              if (filtroActivo != FiltroNotificacion.todas)
                Positioned(
                  right: 8,
                  top: 8,
                  child: Container(
                    width: 8,
                    height: 8,
                    decoration: const BoxDecoration(
                      color: AppColors.orange,
                      shape: BoxShape.circle,
                    ),
                  ),
                ),
            ],
          ),
        ],
      ),
      body: cargando
          ? const Center(
              child: CircularProgressIndicator(color: AppColors.orange),
            )
          : notificacionesFiltradas.isEmpty
              ? _buildEmptyState()
              : _buildNotificationsList(),
    );
  }

  Widget _buildEmptyState() {
    String mensaje;
    IconData icono;

    if (todasNotificaciones.isEmpty) {
      mensaje = 'No tienes notificaciones';
      icono = Icons.notifications_none;
    } else {
      mensaje = 'No hay notificaciones para "${getNombreFiltro(filtroActivo)}"';
      icono = Icons.filter_list_off;
    }

    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            padding: const EdgeInsets.all(20),
            decoration: BoxDecoration(
              color: AppColors.orange.withOpacity(0.1),
              shape: BoxShape.circle,
            ),
            child: Icon(
              icono,
              size: 48,
              color: AppColors.orange,
            ),
          ),
          const SizedBox(height: 16),
          Text(
            mensaje,
            style: Theme.of(context).textTheme.titleMedium,
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 8),
          if (filtroActivo != FiltroNotificacion.todas) ...[
            TextButton(
              onPressed: () {
                setState(() {
                  filtroActivo = FiltroNotificacion.todas;
                  aplicarFiltro();
                });
              },
              child: const Text(
                'Ver todas las notificaciones',
                style: TextStyle(color: AppColors.orange),
              ),
            ),
          ],
        ],
      ),
    );
  }

  Widget _buildNotificationsList() {
    final grupos = agruparPorFecha(notificacionesFiltradas);
    final ordenCategorias = ["Hoy", "Ayer", "Esta semana", "Anteriores"];

    return Column(
      children: [
        // Header con contador de resultados
        if (filtroActivo != FiltroNotificacion.todas)
          Container(
            width: double.infinity,
            padding: const EdgeInsets.all(16),
            color: AppColors.orange.withOpacity(0.1),
            child: Text(
              '${notificacionesFiltradas.length} resultado${notificacionesFiltradas.length != 1 ? 's' : ''} para "${getNombreFiltro(filtroActivo)}"',
              style: const TextStyle(
                color: AppColors.orange,
                fontWeight: FontWeight.w500,
              ),
            ),
          ),
        
        // Lista agrupada
        Expanded(
          child: ListView(
            padding: const EdgeInsets.all(16),
            children: ordenCategorias
                .where((categoria) => grupos.containsKey(categoria))
                .map((categoria) => _buildGrupoFecha(categoria, grupos[categoria]!))
                .toList(),
          ),
        ),
      ],
    );
  }

  Widget _buildGrupoFecha(String categoria, List<dynamic> notificaciones) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        // Header de la categoría
        Padding(
          padding: const EdgeInsets.only(left: 4, bottom: 12, top: 16),
          child: Text(
            categoria,
            style: Theme.of(context).textTheme.titleMedium?.copyWith(
              fontWeight: FontWeight.w600,
              color: AppColors.darkGrey,
            ),
          ),
        ),
        
        // Notificaciones de esta categoría
        ...notificaciones.map((notificacion) => 
          _buildNotificationCard(notificacion)).toList(),
      ],
    );
  }

  Widget _buildNotificationCard(Map<String, dynamic> notification) {
    final bool isUnread = notification["estadoNotificacion"] == "NO_LEIDA";
    final String titulo = notification["titulo"] ?? "";
    final String descripcion = notification["descripcion"] ?? "";
    final String tipo = notification["tipoNotificacion"]?["nombreNotificacion"] ?? "";
    final String hora = notification["horaNotificacion"] != null
        ? _formatearHora(notification["horaNotificacion"])
        : "";

    return Container(
      margin: const EdgeInsets.only(bottom: 8),
      child: Material(
        color: AppColors.white,
        borderRadius: BorderRadius.circular(12),
        elevation: isUnread ? 2 : 1,
        shadowColor: AppColors.black.withOpacity(0.1),
        child: InkWell(
          borderRadius: BorderRadius.circular(12),
          onTap: () async {
            final bool wasUnread = notification["estadoNotificacion"] != "LEIDA";
            
            await Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => NotificationDetailPage(
                  notification: notification,
                ),
              ),
            );
            
            if (wasUnread) {
              await notificacionService.marcarLeida(notification["idNotificacion"]);
              await cargarNotificaciones();
            }
          },
          child: Container(
            padding: const EdgeInsets.all(16),
            child: Row(
              children: [
                // Indicador de estado
                Container(
                  width: 8,
                  height: 8,
                  decoration: BoxDecoration(
                    color: isUnread ? AppColors.orange : Colors.transparent,
                    shape: BoxShape.circle,
                  ),
                ),
                const SizedBox(width: 12),
                
                // Contenido
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        children: [
                          // Badge del tipo
                          Container(
                            padding: const EdgeInsets.symmetric(
                              horizontal: 8,
                              vertical: 2,
                            ),
                            decoration: BoxDecoration(
                              color: _getTipoColor(tipo).withOpacity(0.1),
                              borderRadius: BorderRadius.circular(6),
                            ),
                            child: Text(
                              tipo,
                              style: TextStyle(
                                color: _getTipoColor(tipo),
                                fontSize: 10,
                                fontWeight: FontWeight.w600,
                              ),
                            ),
                          ),
                          const Spacer(),
                          
                          // Hora
                          if (hora.isNotEmpty)
                            Text(
                              hora,
                              style: TextStyle(
                                color: AppColors.mediumGrey,
                                fontSize: 12,
                                fontWeight: FontWeight.w500,
                              ),
                            ),
                        ],
                      ),
                      const SizedBox(height: 8),
                      
                      // Título
                      Text(
                        titulo,
                        style: Theme.of(context).textTheme.titleSmall?.copyWith(
                          fontWeight: isUnread ? FontWeight.w600 : FontWeight.w500,
                          color: isUnread ? AppColors.black : AppColors.darkGrey,
                        ),
                      ),
                      const SizedBox(height: 4),
                      
                      // Descripción
                      Text(
                        descripcion,
                        style: Theme.of(context).textTheme.bodySmall?.copyWith(
                          color: AppColors.mediumGrey,
                          height: 1.3,
                        ),
                        maxLines: 2,
                        overflow: TextOverflow.ellipsis,
                      ),
                    ],
                  ),
                ),
                
                const SizedBox(width: 8),
                
                // Flecha
                Icon(
                  Icons.chevron_right,
                  color: AppColors.mediumGrey,
                  size: 20,
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Color _getTipoColor(String tipo) {
    switch (tipo.toUpperCase()) {
      case "FAVORITO":
        return Colors.pink;
      case "ESCANEO":
        return Colors.green;
      case "ADMIN":
        return Colors.purple;
      case "EVENTO":
        return Colors.blue;
      case "REPORTE":
        return Colors.teal;
      default:
        return AppColors.orange;
    }
  }

  String _formatearHora(String horaCompleta) {
    try {
      final DateTime dateTime = DateTime.parse(horaCompleta);
      return '${dateTime.hour.toString().padLeft(2, '0')}:${dateTime.minute.toString().padLeft(2, '0')}';
    } catch (e) {
      return '';
    }
  }
}