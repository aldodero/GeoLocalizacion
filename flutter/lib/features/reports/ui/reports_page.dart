import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../../../service/reporte_service.dart';

class ReportsPage extends StatefulWidget {
  final int idLocal;
  
  const ReportsPage({
    super.key,
    required this.idLocal,
  });

  @override
  State<ReportsPage> createState() => _ReportsPageState();
}

class _ReportsPageState extends State<ReportsPage> {
  final ReporteService reporteService = ReporteService();
  List<dynamic> reportes = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    cargarReportes();
  }


//metodo para cargar reportes
  Future<void> cargarReportes() async {
    setState(() => isLoading = true);

    print("CARGANDO REPORTES LCOAL: ${widget.idLocal}");

    final data = await reporteService.obtenerReportesUsuarioLocal(
      1,
      widget.idLocal,
      );

    setState(() {
      reportes = data;
      isLoading = false;
    });
  }




  void mostrarFormularioReporte() {
    print("LOCAL ACTIVO EN REPORTES: ${widget.idLocal}");
    
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: Colors.transparent,

      builder: (context) => _FormularioReporte(
        idLocal: widget.idLocal,
        onReporteCreado: () {
          Navigator.pop(context);
          cargarReportes();
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text("Reporte enviado correctamente"),
              backgroundColor: AppColors.orange,
            ),
          );
        },
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        title: const Text("Reportes"),
        backgroundColor: AppColors.white,
        elevation: 0,
        iconTheme: const IconThemeData(color: AppColors.black),
        titleTextStyle: const TextStyle(
          color: AppColors.black,
          fontSize: 18,
          fontWeight: FontWeight.w600,
        ),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: cargarReportes,
          ),
        ],
      ),
      body: isLoading
          ? const Center(
              child: CircularProgressIndicator(
                color: AppColors.orange,
              ),
            )
          : RefreshIndicator(
              color: AppColors.orange,
              onRefresh: cargarReportes,
              child: reportes.isEmpty
                  ? _EmptyState(onCrearReporte: mostrarFormularioReporte)
                  : ListView.builder(
                      padding: const EdgeInsets.all(16),
                      itemCount: reportes.length,
                      itemBuilder: (context, index) {
                        final reporte = reportes[index];
                        return _ReporteCard(reporte: reporte);
                      },
                    ),
            ),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: mostrarFormularioReporte,
        backgroundColor: AppColors.orange,
        icon: const Icon(Icons.add),
        label: const Text("Crear reporte"),
      ),
    );
  }
}

class _EmptyState extends StatelessWidget {
  final VoidCallback onCrearReporte;

  const _EmptyState({required this.onCrearReporte});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Container(
              width: 120,
              height: 120,
              decoration: BoxDecoration(
                color: AppColors.orange.withOpacity(0.1),
                borderRadius: BorderRadius.circular(60),
              ),
              child: const Icon(
                Icons.report_problem_outlined,
                size: 60,
                color: AppColors.orange,
              ),
            ),
            const SizedBox(height: 24),
            const Text(
              "No tienes reportes",
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
                color: AppColors.black,
              ),
            ),
            const SizedBox(height: 12),
            const Text(
              "Reporta problemas como productos no encontrados, ubicaciones incorrectas o stock erróneo",
              textAlign: TextAlign.center,
              style: TextStyle(
                fontSize: 14,
                color: AppColors.mediumGrey,
                height: 1.5,
              ),
            ),
            const SizedBox(height: 32),
            ElevatedButton.icon(
              onPressed: onCrearReporte,
              icon: const Icon(Icons.add),
              label: const Text("Crear primer reporte"),
              style: ElevatedButton.styleFrom(
                backgroundColor: AppColors.orange,
                foregroundColor: AppColors.white,
                padding: const EdgeInsets.symmetric(
                  horizontal: 24,
                  vertical: 14,
                ),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _ReporteCard extends StatelessWidget {
  final Map<String, dynamic> reporte;

  const _ReporteCard({required this.reporte});

  Color _getEstadoColor(String? estado) {
    switch (estado?.toUpperCase()) {
      case "PENDIENTE":
        return Colors.amber.shade700; // Amarillo más fuerte
      case "EN_REVISION":
        return Colors.blue.shade600;
      case "RESUELTO":
        return Colors.green.shade600;
      case "RECHAZADO":
        return Colors.red.shade600;
      default:
        return AppColors.mediumGrey;
    }
  }

  IconData _getTipoIcon(String? tipo) {
    final tipoUpper = tipo?.toUpperCase() ?? "";
    switch (tipoUpper) {
      case "PRODUCTO_NO_ENCONTRADO":
        return Icons.search_off;
      case "UBICACION_INCORRECTA":
        return Icons.location_off;
      case "GONDOLA_EQUIVOCADA":
        return Icons.shelves;
      case "PRODUCTO_VENCIDO":
        return Icons.event_busy;
      case "STOCK_INCORRECTO":
        return Icons.inventory_2_outlined;
      case "CODIGO_DANADO":
        return Icons.qr_code_2;
      case "MAPA_INCORRECTO":
        return Icons.map_outlined;
      case "OTRO":
        return Icons.report_problem;
      default:
        return Icons.report_problem;
    }
  }

  String _getTipoTexto(String? tipo) {
    final tipoUpper = tipo?.toUpperCase() ?? "";
    switch (tipoUpper) {
      case "PRODUCTO_NO_ENCONTRADO":
        return "Producto no encontrado";
      case "UBICACION_INCORRECTA":
        return "Ubicación incorrecta";
      case "GONDOLA_EQUIVOCADA":
        return "Góndola equivocada";
      case "PRODUCTO_VENCIDO":
        return "Producto vencido";
      case "STOCK_INCORRECTO":
        return "Stock incorrecto";
      case "CODIGO_DANADO":
        return "Código dañado";
      case "MAPA_INCORRECTO":
        return "Mapa incorrecto";
      case "OTRO":
        return "Otro problema";
      default:
        return "Otro problema";
    }
  }

  Color _getPrioridadColor(String? prioridad) {
    switch (prioridad?.toUpperCase()) {
      case "ALTA":
        return Colors.red;
      case "MEDIA":
        return Colors.orange;
      case "BAJA":
        return Colors.grey;
      default:
        return Colors.grey;
    }
  }

  @override
  Widget build(BuildContext context) {
    final estadoReporte = reporte["estadoReporte"] ?? reporte["estado"] ?? "PENDIENTE";
    final estadoColor = _getEstadoColor(estadoReporte);
    final tipoReporte = reporte["tipoReporte"]?["nombreTipoReporte"] ?? "";
    
    // Debug: ver qué valor llega del backend
    print("DEBUG ESTADO REPORTE: '$estadoReporte'");
    print("DEBUG TIPO REPORTE: '$tipoReporte'");
    print("DEBUG TIPO REPORTE UPPER: '${tipoReporte.toString().toUpperCase()}'");

    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      decoration: BoxDecoration(
        color: AppColors.white,
        borderRadius: BorderRadius.circular(16),
        border: Border.all(
          color: estadoColor.withOpacity(0.3),
          width: 1,
        ),
        boxShadow: [
          BoxShadow(
            color: AppColors.black.withOpacity(0.06),
            blurRadius: 12,
            offset: const Offset(0, 3),
          ),
        ],
      ),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Container(
                  width: 40,
                  height: 40,
                  decoration: BoxDecoration(
                    color: estadoColor.withOpacity(0.1),
                    borderRadius: BorderRadius.circular(10),
                  ),
                  child: Icon(
                    _getTipoIcon(tipoReporte),
                    color: estadoColor,
                    size: 20,
                  ),
                ),
                const SizedBox(width: 12),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        _getTipoTexto(tipoReporte),
                        style: const TextStyle(
                          fontSize: 15,
                          fontWeight: FontWeight.w600,
                          color: AppColors.black,
                        ),
                      ),
                      const SizedBox(height: 2),
                      Text(
                        reporte["fechaCreacion"] ?? "",
                        style: const TextStyle(
                          fontSize: 12,
                          color: AppColors.mediumGrey,
                        ),
                      ),
                    ],
                  ),
                ),
                Container(
                  padding: const EdgeInsets.symmetric(
                    horizontal: 12,
                    vertical: 6,
                  ),
                  decoration: BoxDecoration(
                    color: estadoColor.withOpacity(0.2),
                    borderRadius: BorderRadius.circular(8),
                    border: Border.all(
                      color: estadoColor,
                      width: 1.5,
                    ),
                  ),
                  child: Text(
                    estadoReporte.toString().toUpperCase(),
                    style: TextStyle(
                      fontSize: 11,
                      fontWeight: FontWeight.w700,
                      color: estadoColor,
                      letterSpacing: 0.5,
                    ),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 12),
            Text(
              reporte["descripcion"] ?? "Sin descripción",
              style: const TextStyle(
                fontSize: 13,
                color: AppColors.darkGrey,
                height: 1.4,
              ),
              maxLines: 2,
              overflow: TextOverflow.ellipsis,
            ),
            if (reporte["prioridad"] != null) ...[
              const SizedBox(height: 12),
              Container(
                padding: const EdgeInsets.symmetric(
                  horizontal: 10,
                  vertical: 6,
                ),
                decoration: BoxDecoration(
                  color: _getPrioridadColor(reporte["prioridad"]).withOpacity(0.15),
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(
                    color: _getPrioridadColor(reporte["prioridad"]),
                    width: 1,
                  ),
                ),
                child: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    Icon(
                      Icons.flag,
                      size: 14,
                      color: _getPrioridadColor(reporte["prioridad"]),
                    ),
                    const SizedBox(width: 6),
                    Text(
                      "Prioridad: ${reporte["prioridad"]}",
                      style: TextStyle(
                        fontSize: 12,
                        color: _getPrioridadColor(reporte["prioridad"]),
                        fontWeight: FontWeight.w600,
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ],
        ),
      ),
    );
  }
}

class _FormularioReporte extends StatefulWidget {
  final VoidCallback onReporteCreado;
  final int idLocal;

  const _FormularioReporte({required this.onReporteCreado, required this.idLocal});

  @override
  State<_FormularioReporte> createState() => _FormularioReporteState();
}

class _FormularioReporteState extends State<_FormularioReporte> {
  final ReporteService reporteService = ReporteService();
  final TextEditingController descripcionController = TextEditingController();
  
  int tipoSeleccionadoId = 1;
  String prioridadSeleccionada = "MEDIA";
  bool enviando = false;

  final List<Map<String, dynamic>> tiposReporte = [
    {"id": 1, "value": "PRODUCTO_NO_ENCONTRADO", "label": "Producto no encontrado", "icon": Icons.search_off},
    {"id": 2, "value": "UBICACION_INCORRECTA", "label": "Ubicación incorrecta", "icon": Icons.location_off},
    {"id": 3, "value": "GONDOLA_EQUIVOCADA", "label": "Góndola equivocada", "icon": Icons.shelves},
    {"id": 4, "value": "PRODUCTO_VENCIDO", "label": "Producto vencido", "icon": Icons.event_busy},
    {"id": 5, "value": "STOCK_INCORRECTO", "label": "Stock incorrecto", "icon": Icons.inventory_2_outlined},
    {"id": 6, "value": "CODIGO_DANADO", "label": "Código dañado", "icon": Icons.qr_code_2},
    {"id": 7, "value": "MAPA_INCORRECTO", "label": "Mapa incorrecto", "icon": Icons.map_outlined},
    {"id": 8, "value": "OTRO", "label": "Otro problema", "icon": Icons.report_problem},
  ];

  Future<void> enviarReporte() async {
    if (descripcionController.text.trim().isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text("Por favor describe el problema"),
          backgroundColor: Colors.red,
        ),
      );
      return;
    }

    setState(() => enviando = true);

    final tipoSeleccionado = tiposReporte.firstWhere((tipo) => tipo["id"] == tipoSeleccionadoId);

    final data = {
      "idUsuario": 1,
      "idLocal": widget.idLocal,
      "idTipo": tipoSeleccionado["id"],
      "descripcion": descripcionController.text.trim(),
      "nombreReporte": tipoSeleccionado["value"],
      "prioridad": prioridadSeleccionada,
    };

    print("LOCAL ACTIVO: ${widget.idLocal}");
    print("PAYLOAD REPORTE: $data");

    final success = await reporteService.crearReporte(data);

    setState(() => enviando = false);

    if (success) {
      widget.onReporteCreado();
    } else {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text("Error al enviar el reporte"),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        color: AppColors.white,
        borderRadius: BorderRadius.vertical(top: Radius.circular(24)),
      ),
      padding: EdgeInsets.only(
        bottom: MediaQuery.of(context).viewInsets.bottom,
      ),
      child: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(20),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Center(
                child: Container(
                  width: 40,
                  height: 4,
                  decoration: BoxDecoration(
                    color: AppColors.mediumGrey,
                    borderRadius: BorderRadius.circular(10),
                  ),
                ),
              ),
              const SizedBox(height: 20),
              const Text(
                "Crear reporte",
                style: TextStyle(
                  fontSize: 22,
                  fontWeight: FontWeight.bold,
                  color: AppColors.black,
                ),
              ),
              const SizedBox(height: 8),
              const Text(
                "Describe el problema que encontraste",
                style: TextStyle(
                  fontSize: 14,
                  color: AppColors.mediumGrey,
                ),
              ),
              const SizedBox(height: 24),

              // Tipo de reporte
              const Text(
                "Tipo de problema",
                style: TextStyle(
                  fontSize: 14,
                  fontWeight: FontWeight.w600,
                  color: AppColors.black,
                ),
              ),
              const SizedBox(height: 12),
              DropdownButtonFormField<int>(
                value: tipoSeleccionadoId,
                decoration: InputDecoration(
                  filled: true,
                  fillColor: AppColors.lightGrey,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(12),
                    borderSide: BorderSide.none,
                  ),
                  contentPadding: const EdgeInsets.symmetric(
                    horizontal: 16,
                    vertical: 14,
                  ),
                ),
                items: tiposReporte.map<DropdownMenuItem<int>>((tipo) {
                  return DropdownMenuItem<int>(
                    value: tipo["id"] as int,
                    child: Row(
                      children: [
                        Icon(tipo["icon"] as IconData, size: 20, color: AppColors.orange),
                        const SizedBox(width: 12),
                        Text(tipo["label"] as String),
                      ],
                    ),
                  );
                }).toList(),
                onChanged: (value) {
                  setState(() => tipoSeleccionadoId = value!);
                },
              ),

              const SizedBox(height: 20),

              // Prioridad
              const Text(
                "Prioridad",
                style: TextStyle(
                  fontSize: 14,
                  fontWeight: FontWeight.w600,
                  color: AppColors.black,
                ),
              ),
              const SizedBox(height: 12),
              Row(
                children: [
                  Expanded(
                    child: _PrioridadChip(
                      label: "BAJA",
                      isSelected: prioridadSeleccionada == "BAJA",
                      color: Colors.grey,
                      onTap: () => setState(() => prioridadSeleccionada = "BAJA"),
                    ),
                  ),
                  const SizedBox(width: 8),
                  Expanded(
                    child: _PrioridadChip(
                      label: "MEDIA",
                      isSelected: prioridadSeleccionada == "MEDIA",
                      color: Colors.orange,
                      onTap: () => setState(() => prioridadSeleccionada = "MEDIA"),
                    ),
                  ),
                  const SizedBox(width: 8),
                  Expanded(
                    child: _PrioridadChip(
                      label: "ALTA",
                      isSelected: prioridadSeleccionada == "ALTA",
                      color: Colors.red,
                      onTap: () => setState(() => prioridadSeleccionada = "ALTA"),
                    ),
                  ),
                ],
              ),

              const SizedBox(height: 20),

              // Descripción
              const Text(
                "Descripción",
                style: TextStyle(
                  fontSize: 14,
                  fontWeight: FontWeight.w600,
                  color: AppColors.black,
                ),
              ),
              const SizedBox(height: 12),
              TextField(
                controller: descripcionController,
                maxLines: 4,
                decoration: InputDecoration(
                  hintText: "Describe el problema en detalle...",
                  filled: true,
                  fillColor: AppColors.lightGrey,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(12),
                    borderSide: BorderSide.none,
                  ),
                  contentPadding: const EdgeInsets.all(16),
                ),
              ),

              const SizedBox(height: 24),

              // Botón enviar
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: enviando ? null : enviarReporte,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: AppColors.orange,
                    foregroundColor: AppColors.white,
                    padding: const EdgeInsets.symmetric(vertical: 16),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(12),
                    ),
                    elevation: 0,
                  ),
                  child: enviando
                      ? const SizedBox(
                          height: 20,
                          width: 20,
                          child: CircularProgressIndicator(
                            color: AppColors.white,
                            strokeWidth: 2,
                          ),
                        )
                      : const Text(
                          "Enviar reporte",
                          style: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class _PrioridadChip extends StatelessWidget {
  final String label;
  final bool isSelected;
  final Color color;
  final VoidCallback onTap;

  const _PrioridadChip({
    required this.label,
    required this.isSelected,
    required this.color,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        padding: const EdgeInsets.symmetric(vertical: 12),
        decoration: BoxDecoration(
          color: isSelected ? color.withOpacity(0.15) : AppColors.lightGrey,
          borderRadius: BorderRadius.circular(12),
          border: Border.all(
            color: isSelected ? color : Colors.transparent,
            width: 2,
          ),
        ),
        child: Center(
          child: Text(
            label,
            style: TextStyle(
              fontSize: 13,
              fontWeight: FontWeight.w600,
              color: isSelected ? color : AppColors.mediumGrey,
            ),
          ),
        ),
      ),
    );
  }
}
