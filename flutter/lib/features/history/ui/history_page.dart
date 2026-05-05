import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../../../service/historial_service.dart';

class HistoryPage extends StatefulWidget {
  const HistoryPage({super.key});

  @override
  State<HistoryPage> createState() => _HistoryPageState();
}

class _HistoryPageState extends State<HistoryPage> {
  final HistorialService historialService = HistorialService();

  List<dynamic> historial = [];
  bool cargando = true;

  @override
  void initState() {
    super.initState();
    cargarHistorial();
  }

  Future<void> cargarHistorial() async {
    final data = await historialService.obtenerHistorial();

    setState(() {
      historial = data;
      cargando = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        title: const Text('Historial'),
        automaticallyImplyLeading: false,
      ),
      body: cargando
          ? const Center(child: CircularProgressIndicator())
          : historial.isEmpty
              ? _emptyState(context)
              : _list(context),
    );
  }

  Widget _list(BuildContext context) {
    return ListView.builder(
      padding: const EdgeInsets.all(16),
      itemCount: historial.length,
      itemBuilder: (context, index) {
        final item = historial[index];

        return _HistoryCard(
          nombre: item["nombreProducto"] ?? "",
          codigo: item["codigoProducto"] ?? "",
          ubicacion: item["tipo"] ?? "",
          hora: "Ahora", // 🔥 después lo conectamos con fecha real
        );
      },
    );
  }

  Widget _emptyState(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            width: 72,
            height: 72,
            decoration: BoxDecoration(
              color: AppColors.black.withOpacity(0.06),
              borderRadius: BorderRadius.circular(20),
            ),
            child: const Icon(
              Icons.history,
              size: 36,
              color: AppColors.darkGrey,
            ),
          ),
          const SizedBox(height: 16),
          Text(
            'Sin historial aún',
            style: Theme.of(context).textTheme.titleMedium,
          ),
          const SizedBox(height: 6),
          Text(
            'Los productos que busques aparecerán aquí',
            style: Theme.of(context).textTheme.bodySmall,
            textAlign: TextAlign.center,
          ),
        ],
      ),
    );
  }
}

class _HistoryCard extends StatelessWidget {
  final String nombre;
  final String codigo;
  final String ubicacion;
  final String hora;

  const _HistoryCard({
    required this.nombre,
    required this.codigo,
    required this.ubicacion,
    required this.hora,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(bottom: 12),
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: AppColors.white,
        borderRadius: BorderRadius.circular(16),
        boxShadow: [
          BoxShadow(
            color: AppColors.black.withOpacity(0.05),
            blurRadius: 10,
            offset: const Offset(0, 2),
          ),
        ],
      ),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            width: 44,
            height: 44,
            decoration: BoxDecoration(
              color: AppColors.black.withOpacity(0.06),
              borderRadius: BorderRadius.circular(12),
            ),
            child: const Icon(
              Icons.history,
              color: AppColors.darkGrey,
              size: 22,
            ),
          ),
          const SizedBox(width: 14),

          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(nombre, style: Theme.of(context).textTheme.titleMedium),
                const SizedBox(height: 3),
                Text(
                  'Cód: $codigo',
                  style: Theme.of(context).textTheme.bodySmall?.copyWith(
                        fontFamily: 'monospace',
                      ),
                ),
                const SizedBox(height: 2),
                Text(ubicacion, style: Theme.of(context).textTheme.bodySmall),
              ],
            ),
          ),

          Text(
            hora,
            style: Theme.of(context).textTheme.bodySmall?.copyWith(
                  fontWeight: FontWeight.w600,
                  color: AppColors.mediumGrey,
                ),
          ),
        ],
      ),
    );
  }
}