import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../../../service/usuario_local_service.dart';
import '../../map/ui/map_real_page.dart';

class MyLocationsPage extends StatefulWidget {
  final int localActivoId;
  const MyLocationsPage({super.key, required this.localActivoId});

  @override
  State<MyLocationsPage> createState() => _MyLocationsPageState();
}




class _MyLocationsPageState extends State<MyLocationsPage> {
  final UsuarioLocalService usuarioLocalService = UsuarioLocalService();
  List<dynamic> locales = [];
  bool isLoading = true;
  int? localActivoId;

  @override
  void initState() {
    super.initState();
    localActivoId = widget.localActivoId;
    cargarLocales();
  }

  Future<void> cargarLocales() async {
    setState(() => isLoading = true);
    
    final data = await usuarioLocalService.obtenerLocalesUsuario(1);
    
    setState(() {
      locales = data;
      isLoading = false;
    });
  }



  void seleccionarLocal(int idLocal) {
    Navigator.pop(
      context,
      idLocal
    );
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        title: const Text("Mis locales"),
        backgroundColor: AppColors.white,
        elevation: 0,
        iconTheme: const IconThemeData(color: AppColors.black),
        titleTextStyle: const TextStyle(
          color: AppColors.black,
          fontSize: 18,
          fontWeight: FontWeight.w600,
        ),
      ),
      body: isLoading
          ? const Center(
              child: CircularProgressIndicator(
                color: AppColors.orange,
              ),
            )
          : locales.isEmpty
              ? Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Container(
                        width: 100,
                        height: 100,
                        decoration: BoxDecoration(
                          color: AppColors.mediumGrey.withOpacity(0.2),
                          borderRadius: BorderRadius.circular(16),
                        ),
                        child: const Icon(
                          Icons.store_outlined,
                          color: AppColors.mediumGrey,
                          size: 50,
                        ),
                      ),
                      const SizedBox(height: 20),
                      const Text(
                        "No tienes locales asignados",
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.w600,
                          color: AppColors.darkGrey,
                        ),
                      ),
                      const SizedBox(height: 8),
                      const Text(
                        "Contacta al administrador",
                        style: TextStyle(
                          fontSize: 14,
                          color: AppColors.mediumGrey,
                        ),
                      ),
                    ],
                  ),
                )
              : ListView.builder(
                  padding: const EdgeInsets.all(16),
                  itemCount: locales.length,
                  itemBuilder: (context, index) {
                    final local = locales[index];
                    final isActivo = local["idLocal"] == localActivoId;

                    return Container(
                      margin: const EdgeInsets.only(bottom: 16),
                      decoration: BoxDecoration(
                        color: AppColors.white,
                        borderRadius: BorderRadius.circular(16),
                        border: isActivo
                            ? Border.all(
                                color: AppColors.orange,
                                width: 2,
                              )
                            : null,
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
                            // Header con ícono y badge
                            Row(
                              children: [
                                Container(
                                  width: 48,
                                  height: 48,
                                  decoration: BoxDecoration(
                                    color: isActivo
                                        ? AppColors.orange
                                        : AppColors.black,
                                    borderRadius: BorderRadius.circular(12),
                                  ),
                                  child: const Icon(
                                    Icons.store,
                                    color: AppColors.white,
                                    size: 24,
                                  ),
                                ),
                                const SizedBox(width: 12),
                                Expanded(
                                  child: Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Text(
                                        local["nombreLocal"] ?? "Sin nombre",
                                        style: const TextStyle(
                                          fontSize: 16,
                                          fontWeight: FontWeight.w600,
                                          color: AppColors.black,
                                        ),
                                      ),
                                      const SizedBox(height: 4),
                                      Text(
                                        local["direccionLocal"] ?? "Sin dirección",
                                        style: const TextStyle(
                                          fontSize: 13,
                                          color: AppColors.mediumGrey,
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                                if (isActivo)
                                  Container(
                                    padding: const EdgeInsets.symmetric(
                                      horizontal: 12,
                                      vertical: 6,
                                    ),
                                    decoration: BoxDecoration(
                                      color: AppColors.orange.withOpacity(0.15),
                                      borderRadius: BorderRadius.circular(8),
                                    ),
                                    child: const Text(
                                      "ACTIVO",
                                      style: TextStyle(
                                        fontSize: 11,
                                        fontWeight: FontWeight.w700,
                                        color: AppColors.orange,
                                        letterSpacing: 0.5,
                                      ),
                                    ),
                                  ),
                              ],
                            ),

                            const SizedBox(height: 16),

                            // Botones de acción
                            Row(
                              children: [
                                if (!isActivo)
                                  Expanded(
                                    child: ElevatedButton.icon(
                                      onPressed: () {
                                        seleccionarLocal(local["idLocal"]);
                                      },
                                      icon: const Icon(
                                        Icons.check_circle_outline,
                                        size: 18,
                                      ),
                                      label: const Text("Seleccionar"),
                                      style: ElevatedButton.styleFrom(
                                        backgroundColor: AppColors.orange,
                                        foregroundColor: AppColors.white,
                                        elevation: 0,
                                        padding: const EdgeInsets.symmetric(
                                          vertical: 12,
                                        ),
                                        shape: RoundedRectangleBorder(
                                          borderRadius: BorderRadius.circular(12),
                                        ),
                                      ),
                                    ),
                                  ),
                                if (!isActivo) const SizedBox(width: 12),
                                Expanded(
                                  child: OutlinedButton.icon(
                                    onPressed: () {
                                      Navigator.push(
                                        context,
                                        MaterialPageRoute(
                                          builder: (context) => MapRealPage(
                                            lat: local["latitud"] ?? 0,
                                            lng: local["longitud"] ?? 0,
                                            nombre: local["nombreLocal"] ?? "",
                                          ),
                                        ),
                                      );
                                    },
                                    icon: const Icon(
                                      Icons.map_outlined,
                                      size: 18,
                                    ),
                                    label: const Text("Ver mapa"),
                                    style: OutlinedButton.styleFrom(
                                      foregroundColor: AppColors.darkGrey,
                                      side: const BorderSide(
                                        color: AppColors.mediumGrey,
                                        width: 1,
                                      ),
                                      padding: const EdgeInsets.symmetric(
                                        vertical: 12,
                                      ),
                                      shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(12),
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ],
                        ),
                      ),
                    );
                  },
                ),
    );
  }
}
