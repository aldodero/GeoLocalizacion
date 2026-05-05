import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';

class FavoritesPage extends StatelessWidget {
  const FavoritesPage({super.key});

  // Simulated data — replace with real data source when available
  static const List<Map<String, String>> _items = [
    {
      'nombre': 'Coca-Cola 1.5L',
      'codigo': '7801234567890',
      'ubicacion': 'Pasillo 5 · Bebidas · B1-B11',
    },
    {
      'nombre': 'Pan molde',
      'codigo': '1234567890123',
      'ubicacion': 'Pasillo 2 · Panadería · A1-A3',
    },
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        title: const Text('Favoritos'),
        automaticallyImplyLeading: false,
      ),
      body: _items.isEmpty ? _emptyState(context) : _list(context),
    );
  }

  Widget _list(BuildContext context) {
    return ListView.builder(
      padding: const EdgeInsets.all(16),
      itemCount: _items.length,
      itemBuilder: (context, index) {
        final item = _items[index];
        return _FavoriteCard(
          nombre: item['nombre']!,
          codigo: item['codigo']!,
          ubicacion: item['ubicacion']!,
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
              color: AppColors.orange.withOpacity(0.1),
              borderRadius: BorderRadius.circular(20),
            ),
            child: const Icon(
              Icons.star_outline,
              size: 36,
              color: AppColors.orange,
            ),
          ),
          const SizedBox(height: 16),
          Text(
            'Sin favoritos aún',
            style: Theme.of(context).textTheme.titleMedium,
          ),
          const SizedBox(height: 6),
          Text(
            'Los productos que marques aparecerán aquí',
            style: Theme.of(context).textTheme.bodySmall,
            textAlign: TextAlign.center,
          ),
        ],
      ),
    );
  }
}

class _FavoriteCard extends StatelessWidget {
  final String nombre;
  final String codigo;
  final String ubicacion;

  const _FavoriteCard({
    required this.nombre,
    required this.codigo,
    required this.ubicacion,
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
        children: [
          // ÍCONO ESTRELLA
          Container(
            width: 44,
            height: 44,
            decoration: BoxDecoration(
              color: AppColors.orange.withOpacity(0.1),
              borderRadius: BorderRadius.circular(12),
            ),
            child: const Icon(Icons.star, color: AppColors.orange, size: 22),
          ),
          const SizedBox(width: 14),

          // INFO
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(nombre, style: Theme.of(context).textTheme.titleMedium),
                const SizedBox(height: 2),
                Text(ubicacion, style: Theme.of(context).textTheme.bodySmall),
              ],
            ),
          ),

          // BOTÓN MAPA
          OutlinedButton(
            onPressed: () {},
            style: OutlinedButton.styleFrom(
              foregroundColor: AppColors.orange,
              side: const BorderSide(color: AppColors.orange),
              padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10),
              ),
              textStyle: const TextStyle(
                fontSize: 12,
                fontWeight: FontWeight.w600,
              ),
            ),
            child: const Text('Mapa'),
          ),
        ],
      ),
    );
  }
}
