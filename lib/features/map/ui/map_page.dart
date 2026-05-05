import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';

class MapPage extends StatelessWidget {
  final double productX;
  final double productY;
  final double userX;
  final double userY;

  const MapPage({
    super.key,
    required this.productX,
    required this.productY,
    required this.userX,
    required this.userY,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        title: const Text('Mapa del supermercado'),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios_new, size: 18),
          onPressed: () => Navigator.pop(context),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            // LEYENDA
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
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
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  _LegendItem(
                    icon: Icons.location_on,
                    color: Colors.red,
                    label: 'Producto',
                  ),
                  _LegendItem(
                    icon: Icons.person_pin_circle,
                    color: Colors.blue,
                    label: 'Tú',
                  ),
                ],
              ),
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
                  child: Stack(
                    children: [
                      // FONDO
                      Container(color: const Color(0xFFF8F8F8)),

                      // PASILLOS
                      _pasillo(20, 50, "P1"),
                      _pasillo(120, 50, "P2"),
                      _pasillo(220, 50, "P3"),

                      // PRODUCTO
                      Positioned(
                        left: productX,
                        top: productY,
                        child: Column(
                          children: const [
                            Icon(Icons.location_on, color: Colors.red, size: 32),
                            SizedBox(height: 2),
                            Text(
                              'Producto',
                              style: TextStyle(
                                fontSize: 10,
                                fontWeight: FontWeight.w600,
                                color: Colors.red,
                              ),
                            ),
                          ],
                        ),
                      ),

                      // USUARIO
                      Positioned(
                        left: userX,
                        top: userY,
                        child: Column(
                          children: const [
                            Icon(Icons.person_pin_circle, color: Colors.blue, size: 32),
                            SizedBox(height: 2),
                            Text(
                              'Tú',
                              style: TextStyle(
                                fontSize: 10,
                                fontWeight: FontWeight.w600,
                                color: Colors.blue,
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _pasillo(double x, double y, String label) {
    return Positioned(
      left: x,
      top: y,
      child: Container(
        width: 60,
        height: 120,
        decoration: BoxDecoration(
          color: const Color(0xFFEEEEEE),
          borderRadius: BorderRadius.circular(8),
        ),
        child: Center(
          child: Text(
            label,
            style: const TextStyle(
              color: AppColors.darkGrey,
              fontWeight: FontWeight.w600,
              fontSize: 13,
            ),
          ),
        ),
      ),
    );
  }
}

class _LegendItem extends StatelessWidget {
  final IconData icon;
  final Color color;
  final String label;

  const _LegendItem({
    required this.icon,
    required this.color,
    required this.label,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Icon(icon, color: color, size: 18),
        const SizedBox(width: 6),
        Text(label, style: Theme.of(context).textTheme.bodySmall),
      ],
    );
  }
}
