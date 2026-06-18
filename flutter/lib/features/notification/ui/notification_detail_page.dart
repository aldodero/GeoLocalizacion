import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';



class NotificationDetailPage extends StatelessWidget {
  final Map<String, dynamic> notification;




  const NotificationDetailPage({
    super.key,
    required this.notification,
  });

  @override
  Widget build(BuildContext context) {
    final String tipo = notification["tipoNotificacion"] != null
        ? notification["tipoNotificacion"]["nombreNotificacion"]
        : "NOTIFICACION";
    
    final String titulo = notification["titulo"] ?? "";
    final String descripcion = notification["descripcion"] ?? "";
    final String estado = notification["estadoNotificacion"] ?? "";
    final String fecha = notification["fechaNotificacion"] ?? "";
    final String hora = notification["horaNotificacion"] != null
        ? notification["horaNotificacion"].toString().substring(11, 16)
        : "";
    
    final bool leida = estado == "LEIDA";

    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        backgroundColor: AppColors.white,
        elevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: AppColors.black),
          onPressed: () => Navigator.pop(context),
        ),
        title: const Text(
          'Notificación',
          style: TextStyle(
            color: AppColors.black,
            fontSize: 18,
            fontWeight: FontWeight.w600,
          ),
        ),
        centerTitle: false,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // TARJETA PRINCIPAL DE NOTIFICACIÓN
            Container(
              width: double.infinity,
              padding: const EdgeInsets.all(24),
              decoration: BoxDecoration(
                color: AppColors.white,
                borderRadius: BorderRadius.circular(16),
                boxShadow: [
                  BoxShadow(
                    color: AppColors.black.withOpacity(0.04),
                    blurRadius: 8,
                    offset: const Offset(0, 2),
                  ),
                ],
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  // TIPO DE NOTIFICACIÓN
                  Container(
                    padding: const EdgeInsets.symmetric(
                      horizontal: 12,
                      vertical: 6,
                    ),
                    decoration: BoxDecoration(
                      color: AppColors.orange.withOpacity(0.1),
                      borderRadius: BorderRadius.circular(8),
                    ),
                    child: Text(
                      tipo,
                      style: const TextStyle(
                        color: AppColors.orange,
                        fontSize: 12,
                        fontWeight: FontWeight.w600,
                        letterSpacing: 0.5,
                      ),
                    ),
                  ),
                  
                  const SizedBox(height: 16),
                  
                  // TÍTULO DE LA NOTIFICACIÓN
                  Text(
                    titulo,
                    style: Theme.of(context).textTheme.titleLarge?.copyWith(
                      fontWeight: FontWeight.w700,
                      color: AppColors.black,
                      height: 1.3,
                    ),
                  ),
                  
                  const SizedBox(height: 20),
                  
                  // SEPARADOR
                  Container(
                    width: double.infinity,
                    height: 1,
                    color: AppColors.mediumGrey.withOpacity(0.2),
                  ),
                  
                  const SizedBox(height: 20),
                  
                  // SECCIÓN DESCRIPCIÓN
                  Text(
                    'Descripción',
                    style: Theme.of(context).textTheme.titleMedium?.copyWith(
                      fontWeight: FontWeight.w600,
                      color: AppColors.black,
                    ),
                  ),
                  
                  const SizedBox(height: 12),
                  
                  // DESCRIPCIÓN DEL EVENTO
                  Text(
                    descripcion,
                    style: Theme.of(context).textTheme.bodyLarge?.copyWith(
                      height: 1.6,
                      color: AppColors.darkGrey,
                    ),
                  ),
                  
                  const SizedBox(height: 24),
                  
                  // INFORMACIÓN DE FECHA Y HORA
                  if (fecha.isNotEmpty || hora.isNotEmpty) ...[
                    Container(
                      padding: const EdgeInsets.all(16),
                      decoration: BoxDecoration(
                        color: AppColors.lightGrey,
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: Row(
                        children: [
                          Icon(
                            Icons.access_time,
                            size: 20,
                            color: AppColors.mediumGrey,
                          ),
                          const SizedBox(width: 12),
                          Text(
                            _formatearFechaHora(fecha, hora),
                            style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                              fontWeight: FontWeight.w500,
                              color: AppColors.darkGrey,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
  
  String _formatearFechaHora(String fecha, String hora) {
    try {
      String resultado = "";
      
      if (fecha.isNotEmpty) {
        final DateTime parsedDate = DateTime.parse(fecha);
        const List<String> meses = [
          'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
          'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
        ];
        
        resultado = '${parsedDate.day}/${parsedDate.month.toString().padLeft(2, '0')}/${parsedDate.year}';
      }
      
      if (hora.isNotEmpty) {
        resultado += resultado.isNotEmpty ? ' - $hora' : hora;
      }
      
      return resultado.isNotEmpty ? resultado : 'Fecha no disponible';
    } catch (e) {
      return hora.isNotEmpty ? hora : 'Fecha no disponible';
    }
  }
}