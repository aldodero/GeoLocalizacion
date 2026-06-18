import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../../../service/historial_service.dart';
import '../../../service/favoritos_service.dart';

class ActivityPage extends StatefulWidget {
  const ActivityPage({super.key});

  @override
  State<ActivityPage> createState() => _ActivityPageState();
}

class _ActivityPageState extends State<ActivityPage> {
  final HistorialService historialService = HistorialService();
  final FavoritosService favoritosService = FavoritosService();
  
  List<dynamic> historial = [];
  List<dynamic> favoritos = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    cargarDatos();
  }

  Future<void> cargarDatos() async {
    setState(() => isLoading = true);

    try {
      final hist = await historialService.obtenerHistorial();
      final favs = await favoritosService.obtenerFavoritos();

      setState(() {
        historial = hist;
        favoritos = favs;
        isLoading = false;
      });
    } catch (e) {
      print("ERROR CARGANDO ACTIVIDAD: $e");
      setState(() => isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        title: const Text("Mi actividad"),
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
            onPressed: cargarDatos,
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
              onRefresh: cargarDatos,
              child: SingleChildScrollView(
                physics: const AlwaysScrollableScrollPhysics(),
                padding: const EdgeInsets.all(16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    // Título sección
                    const Text(
                      "Resumen de actividad",
                      style: TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.bold,
                        color: AppColors.black,
                      ),
                    ),
                    const SizedBox(height: 16),

                    // Cards de estadísticas
                    Row(
                      children: [
                        Expanded(
                          child: _StatCard(
                            icon: Icons.history,
                            title: "Búsquedas",
                            value: historial.length.toString(),
                            color: AppColors.orange,
                          ),
                        ),
                        const SizedBox(width: 12),
                        Expanded(
                          child: _StatCard(
                            icon: Icons.star,
                            title: "Favoritos",
                            value: favoritos.length.toString(),
                            color: AppColors.black,
                          ),
                        ),
                      ],
                    ),

                    const SizedBox(height: 32),

                    // Búsquedas recientes
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        const Text(
                          "Búsquedas recientes",
                          style: TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                            color: AppColors.black,
                          ),
                        ),
                        if (historial.isNotEmpty)
                          TextButton(
                            onPressed: () {
                              // Navegar a historial completo
                            },
                            child: const Text(
                              "Ver todo",
                              style: TextStyle(
                                color: AppColors.orange,
                                fontSize: 14,
                              ),
                            ),
                          ),
                      ],
                    ),
                    const SizedBox(height: 12),

                    historial.isEmpty
                        ? Container(
                            padding: const EdgeInsets.all(24),
                            decoration: BoxDecoration(
                              color: AppColors.white,
                              borderRadius: BorderRadius.circular(16),
                            ),
                            child: const Center(
                              child: Column(
                                children: [
                                  Icon(
                                    Icons.search_off,
                                    size: 48,
                                    color: AppColors.mediumGrey,
                                  ),
                                  SizedBox(height: 12),
                                  Text(
                                    "No hay búsquedas registradas",
                                    style: TextStyle(
                                      color: AppColors.mediumGrey,
                                      fontSize: 14,
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          )
                        : Column(
                            children: historial.take(5).map((item) {
                              return Container(
                                margin: const EdgeInsets.only(bottom: 12),
                                padding: const EdgeInsets.all(16),
                                decoration: BoxDecoration(
                                  color: AppColors.white,
                                  borderRadius: BorderRadius.circular(12),
                                  boxShadow: [
                                    BoxShadow(
                                      color: AppColors.black.withOpacity(0.04),
                                      blurRadius: 8,
                                      offset: const Offset(0, 2),
                                    ),
                                  ],
                                ),
                                child: Row(
                                  children: [
                                    Container(
                                      width: 40,
                                      height: 40,
                                      decoration: BoxDecoration(
                                        color: AppColors.orange.withOpacity(0.1),
                                        borderRadius: BorderRadius.circular(10),
                                      ),
                                      child: const Icon(
                                        Icons.shopping_bag_outlined,
                                        color: AppColors.orange,
                                        size: 20,
                                      ),
                                    ),
                                    const SizedBox(width: 12),
                                    Expanded(
                                      child: Column(
                                        crossAxisAlignment: CrossAxisAlignment.start,
                                        children: [
                                          Text(
                                            item["nombreProducto"] ?? "Producto",
                                            style: const TextStyle(
                                              fontSize: 14,
                                              fontWeight: FontWeight.w600,
                                              color: AppColors.black,
                                            ),
                                          ),
                                          const SizedBox(height: 2),
                                          Text(
                                            item["nombreLocal"] ?? "Local",
                                            style: const TextStyle(
                                              fontSize: 12,
                                              color: AppColors.mediumGrey,
                                            ),
                                          ),
                                        ],
                                      ),
                                    ),
                                    Text(
                                      item["hora"]?.toString().substring(11, 16) ?? "",
                                      style: const TextStyle(
                                        fontSize: 11,
                                        color: AppColors.mediumGrey,
                                      ),
                                    ),
                                  ],
                                ),
                              );
                            }).toList(),
                          ),

                    const SizedBox(height: 32),

                    // Favoritos recientes
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        const Text(
                          "Favoritos recientes",
                          style: TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                            color: AppColors.black,
                          ),
                        ),
                        if (favoritos.isNotEmpty)
                          TextButton(
                            onPressed: () {
                              // Navegar a favoritos completo
                            },
                            child: const Text(
                              "Ver todo",
                              style: TextStyle(
                                color: AppColors.orange,
                                fontSize: 14,
                              ),
                            ),
                          ),
                      ],
                    ),
                    const SizedBox(height: 12),

                    favoritos.isEmpty
                        ? Container(
                            padding: const EdgeInsets.all(24),
                            decoration: BoxDecoration(
                              color: AppColors.white,
                              borderRadius: BorderRadius.circular(16),
                            ),
                            child: const Center(
                              child: Column(
                                children: [
                                  Icon(
                                    Icons.star_outline,
                                    size: 48,
                                    color: AppColors.mediumGrey,
                                  ),
                                  SizedBox(height: 12),
                                  Text(
                                    "No tienes favoritos guardados",
                                    style: TextStyle(
                                      color: AppColors.mediumGrey,
                                      fontSize: 14,
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          )
                        : Column(
                            children: favoritos.take(3).map((item) {
                              return Container(
                                margin: const EdgeInsets.only(bottom: 12),
                                padding: const EdgeInsets.all(16),
                                decoration: BoxDecoration(
                                  color: AppColors.white,
                                  borderRadius: BorderRadius.circular(12),
                                  boxShadow: [
                                    BoxShadow(
                                      color: AppColors.black.withOpacity(0.04),
                                      blurRadius: 8,
                                      offset: const Offset(0, 2),
                                    ),
                                  ],
                                ),
                                child: Row(
                                  children: [
                                    Container(
                                      width: 40,
                                      height: 40,
                                      decoration: BoxDecoration(
                                        color: AppColors.black.withOpacity(0.1),
                                        borderRadius: BorderRadius.circular(10),
                                      ),
                                      child: const Icon(
                                        Icons.star,
                                        color: AppColors.orange,
                                        size: 20,
                                      ),
                                    ),
                                    const SizedBox(width: 12),
                                    Expanded(
                                      child: Column(
                                        crossAxisAlignment: CrossAxisAlignment.start,
                                        children: [
                                          Text(
                                            item["nombreProducto"] ?? "Producto",
                                            style: const TextStyle(
                                              fontSize: 14,
                                              fontWeight: FontWeight.w600,
                                              color: AppColors.black,
                                            ),
                                          ),
                                          const SizedBox(height: 2),
                                          Text(
                                            item["codigoProducto"] ?? "",
                                            style: const TextStyle(
                                              fontSize: 12,
                                              color: AppColors.mediumGrey,
                                            ),
                                          ),
                                        ],
                                      ),
                                    ),
                                  ],
                                ),
                              );
                            }).toList(),
                          ),

                    const SizedBox(height: 24),
                  ],
                ),
              ),
            ),
    );
  }
}

class _StatCard extends StatelessWidget {
  final IconData icon;
  final String title;
  final String value;
  final Color color;

  const _StatCard({
    required this.icon,
    required this.title,
    required this.value,
    required this.color,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: AppColors.white,
        borderRadius: BorderRadius.circular(16),
        boxShadow: [
          BoxShadow(
            color: AppColors.black.withOpacity(0.06),
            blurRadius: 12,
            offset: const Offset(0, 3),
          ),
        ],
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            width: 40,
            height: 40,
            decoration: BoxDecoration(
              color: color.withOpacity(0.1),
              borderRadius: BorderRadius.circular(10),
            ),
            child: Icon(
              icon,
              color: color,
              size: 20,
            ),
          ),
          const SizedBox(height: 12),
          Text(
            value,
            style: const TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.bold,
              color: AppColors.black,
            ),
          ),
          const SizedBox(height: 4),
          Text(
            title,
            style: const TextStyle(
              fontSize: 12,
              color: AppColors.mediumGrey,
            ),
          ),
        ],
      ),
    );
  }
}
