import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../../map/ui/map_page.dart';
import '../../map/ui/map_real_page.dart';

class ProductResultPage extends StatelessWidget {
  final String nombre;
  final String codigo;
  final String ubicacion;
  final double productX;
  final double productY;

  const ProductResultPage({
    super.key,
    required this.nombre,
    this.codigo = "7801234567890",
    this.ubicacion = "Pasillo 5 - Bebidas - B1-B11",
    this.productX = 150,
    this.productY = 200,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        title: const Text('Resultado'),
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
            // CARD PRODUCTO
            Container(
              width: double.infinity,
              padding: const EdgeInsets.all(20),
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
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  // ÍCONO + NOMBRE
                  Row(
                    children: [
                      Container(
                        width: 52,
                        height: 52,
                        decoration: BoxDecoration(
                          color: AppColors.orange.withOpacity(0.1),
                          borderRadius: BorderRadius.circular(14),
                        ),
                        child: const Icon(
                          Icons.inventory_2_outlined,
                          color: AppColors.orange,
                          size: 26,
                        ),
                      ),
                      const SizedBox(width: 14),
                      Expanded(
                        child: Text(
                          nombre,
                          style: Theme.of(context).textTheme.titleLarge,
                        ),
                      ),
                    ],
                  ),

                  const SizedBox(height: 20),
                  const Divider(height: 1, color: Color(0xFFF0F0F0)),
                  const SizedBox(height: 16),

                  // CÓDIGO
                  _InfoRow(
                    icon: Icons.qr_code,
                    label: 'Código',
                    value: codigo,
                  ),

                  const SizedBox(height: 12),

                  // UBICACIÓN
                  _InfoRow(
                    icon: Icons.location_on_outlined,
                    label: 'Ubicación',
                    value: ubicacion,
                  ),
                ],
              ),
            ),

            const SizedBox(height: 24),

            // BOTÓN MAPA
            SizedBox(
              width: double.infinity,
              child: ElevatedButton.icon(
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                       builder: (context) => MapPage(
                        productX: productX,
                        productY: productY,
                        userX: 50,
                        userY: 300,
                      ),
                    ),
                  );
                },
                icon: const Icon(Icons.map_outlined, size: 20),
                label: const Text('Ver en el mapa'),
                style: ElevatedButton.styleFrom(
                  padding: const EdgeInsets.symmetric(vertical: 16),
                ),
              ),
            ),

            const SizedBox(height: 12),

            // BOTÓN ESCANEAR OTRO
            SizedBox(
              width: double.infinity,
              child: OutlinedButton.icon(
                onPressed: () => Navigator.pop(context),
                icon: const Icon(Icons.qr_code_scanner, size: 20),
                label: const Text('Escanear otro'),
                style: OutlinedButton.styleFrom(
                  padding: const EdgeInsets.symmetric(vertical: 16),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _InfoRow extends StatelessWidget {
  final IconData icon;
  final String label;
  final String value;

  const _InfoRow({
    required this.icon,
    required this.label,
    required this.value,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Icon(icon, size: 18, color: AppColors.orange),
        const SizedBox(width: 10),
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              label,
              style: Theme.of(context).textTheme.bodySmall,
            ),
            const SizedBox(height: 2),
            Text(
              value,
              style: Theme.of(context).textTheme.titleMedium?.copyWith(
                    fontSize: 14,
                  ),
            ),
          ],
        ),
      ],
    );
  }
}
