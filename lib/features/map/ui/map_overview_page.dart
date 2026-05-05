import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';

class MapOverviewPage extends StatelessWidget {
  const MapOverviewPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        title: const Text('Mapa del local'),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios_new, size: 18),
          onPressed: () => Navigator.pop(context),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Distribución general',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            const SizedBox(height: 4),
            Text(
              'Vicuña Mackenna, La Florida',
              style: Theme.of(context).textTheme.bodySmall,
            ),
            const SizedBox(height: 16),

            // MAPA
            Expanded(
              child: Container(
                width: double.infinity,
                decoration: BoxDecoration(
                  color: AppColors.white,
                  borderRadius: BorderRadius.circular(20),
                  boxShadow: [
                    BoxShadow(
                      color: AppColors.black.withOpacity(0.06),
                      blurRadius: 16,
                      offset: const Offset(0, 4),
                    ),
                  ],
                ),
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(20),
                  child: SingleChildScrollView(
                    child: SizedBox(
                      width: double.infinity,
                      height: 520,
                      child: Stack(
                        children: [
                          // FONDO
                          Container(color: const Color(0xFFF8F8F8)),

                          // SECCIONES SUPERIORES
                          _seccion(10, 10, "VERDURAS", const Color(0xFF4CAF50)),
                          _seccion(110, 10, "FRUTAS", const Color(0xFF4CAF50)),
                          _seccion(210, 10, "LÁCTEOS", const Color(0xFF4CAF50)),

                          // PASILLOS IZQUIERDA
                          _pasillo(10, 60, "A1"),
                          _pasillo(10, 110, "A2"),
                          _pasillo(10, 160, "A3"),
                          _pasillo(10, 210, "A4"),
                          _pasillo(10, 260, "A5"),

                          // PASILLOS CENTRO
                          _pasilloCentro(80, 60, "Pasillo 1"),
                          _pasilloCentro(80, 110, "Pasillo 2"),
                          _pasilloCentro(80, 160, "Pasillo 3"),
                          _pasilloCentro(80, 210, "Pasillo 4"),
                          _pasilloCentro(80, 260, "Pasillo 5"),

                          // SECCIONES DERECHA
                          _bloque(220, 60, "BEBIDAS"),
                          _bloque(220, 140, "SNACKS"),
                          _bloque(220, 220, "LIMPIEZA"),

                          // CANES
                          Positioned(
                            left: 10,
                            top: 330,
                            child: Container(
                              width: 70,
                              height: 50,
                              decoration: BoxDecoration(
                                color: const Color(0xFFEF5350),
                                borderRadius: BorderRadius.circular(8),
                              ),
                              child: const Center(
                                child: Text(
                                  "CANES",
                                  style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 11,
                                    fontWeight: FontWeight.w600,
                                  ),
                                ),
                              ),
                            ),
                          ),

                          // CAJAS
                          Positioned(
                            left: 100,
                            top: 330,
                            child: Container(
                              width: 100,
                              height: 50,
                              decoration: BoxDecoration(
                                color: AppColors.orange,
                                borderRadius: BorderRadius.circular(8),
                              ),
                              child: const Center(
                                child: Text(
                                  "CAJAS",
                                  style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 11,
                                    fontWeight: FontWeight.w600,
                                  ),
                                ),
                              ),
                            ),
                          ),

                          // ENTRADA
                          Positioned(
                            left: 110,
                            top: 400,
                            child: Container(
                              width: 100,
                              height: 40,
                              decoration: BoxDecoration(
                                color: const Color(0xFF4CAF50),
                                borderRadius: BorderRadius.circular(8),
                              ),
                              child: const Center(
                                child: Text(
                                  "ENTRADA",
                                  style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 11,
                                    fontWeight: FontWeight.w600,
                                  ),
                                ),
                              ),
                            ),
                          ),

                          // USUARIO
                          const Positioned(
                            left: 150,
                            top: 180,
                            child: Icon(
                              Icons.location_on,
                              color: Colors.blue,
                              size: 30,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _seccion(double x, double y, String texto, Color color) {
    return Positioned(
      left: x,
      top: y,
      child: Container(
        width: 80,
        height: 30,
        decoration: BoxDecoration(
          color: color,
          borderRadius: BorderRadius.circular(6),
        ),
        child: Center(
          child: Text(
            texto,
            style: const TextStyle(
              color: Colors.white,
              fontSize: 10,
              fontWeight: FontWeight.w600,
            ),
          ),
        ),
      ),
    );
  }

  Widget _pasillo(double x, double y, String texto) {
    return Positioned(
      left: x,
      top: y,
      child: Container(
        width: 50,
        height: 40,
        decoration: BoxDecoration(
          color: const Color(0xFF80DEEA),
          borderRadius: BorderRadius.circular(6),
        ),
        child: Center(
          child: Text(
            texto,
            style: const TextStyle(
              fontSize: 11,
              fontWeight: FontWeight.w600,
              color: AppColors.darkGrey,
            ),
          ),
        ),
      ),
    );
  }

  Widget _pasilloCentro(double x, double y, String texto) {
    return Positioned(
      left: x,
      top: y,
      child: Container(
        width: 100,
        height: 40,
        decoration: BoxDecoration(
          color: const Color(0xFFEEEEEE),
          borderRadius: BorderRadius.circular(6),
        ),
        child: Center(
          child: Text(
            texto,
            style: const TextStyle(
              fontSize: 11,
              fontWeight: FontWeight.w600,
              color: AppColors.darkGrey,
            ),
          ),
        ),
      ),
    );
  }

  Widget _bloque(double x, double y, String texto) {
    return Positioned(
      left: x,
      top: y,
      child: Container(
        width: 80,
        height: 60,
        decoration: BoxDecoration(
          color: const Color(0xFFFFF9C4),
          borderRadius: BorderRadius.circular(6),
          border: Border.all(color: const Color(0xFFE0E0E0)),
        ),
        child: Center(
          child: Text(
            texto,
            style: const TextStyle(
              fontSize: 11,
              fontWeight: FontWeight.w600,
              color: AppColors.darkGrey,
            ),
          ),
        ),
      ),
    );
  }
}
