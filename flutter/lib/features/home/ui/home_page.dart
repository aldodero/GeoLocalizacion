import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../../search/ui/search_page.dart';
import '../../favorites/ui/favorites_page.dart';
import '../../history/ui/history_page.dart';
import '../../map/ui/map_overview_page.dart';
import '../../map/ui/map_real_page.dart'; // 🔥 IMPORT NUEVO
import '../../scanner/ui/scanner_page.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    final pages = [
      _homeContent(context),
      const FavoritesPage(),
      const HistoryPage(),
    ];

    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      body: pages[currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: currentIndex,
        onTap: (index) => setState(() => currentIndex = index),
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.home_outlined),
            activeIcon: Icon(Icons.home),
            label: 'Inicio',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.star_outline),
            activeIcon: Icon(Icons.star),
            label: 'Favoritos',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.history),
            activeIcon: Icon(Icons.history),
            label: 'Historial',
          ),
        ],
      ),
    );
  }

  Widget _homeContent(BuildContext context) {
    return SafeArea(
      child: SingleChildScrollView(
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // HEADER
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Row(
                  children: [
                    Container(
                      width: 40,
                      height: 40,
                      decoration: BoxDecoration(
                        color: AppColors.black,
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: const Icon(
                        Icons.person_outline,
                        color: AppColors.white,
                        size: 22,
                      ),
                    ),
                    const SizedBox(width: 10),
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          'Hola, Alejandro 👋',
                          style: Theme.of(context).textTheme.titleMedium,
                        ),
                        Text(
                          'Bienvenido de vuelta',
                          style: Theme.of(context).textTheme.bodySmall,
                        ),
                      ],
                    ),
                  ],
                ),

                Container(
                  width: 40,
                  height: 40,
                  decoration: BoxDecoration(
                    color: AppColors.white,
                    borderRadius: BorderRadius.circular(12),
                    boxShadow: [
                      BoxShadow(
                        color: AppColors.black.withOpacity(0.06),
                        blurRadius: 8,
                        offset: const Offset(0, 2),
                      ),
                    ],
                  ),
                  child: const Icon(
                    Icons.notifications_outlined,
                    color: AppColors.black,
                    size: 20,
                  ),
                ),
              ],
            ),

            const SizedBox(height: 20),

            // 🔥 BANNER UBICACIÓN (CORREGIDO)
            Container(
              width: double.infinity,
              padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 14),
              decoration: BoxDecoration(
                color: AppColors.black,
                borderRadius: BorderRadius.circular(16),
              ),
              child: Row(
                children: [
                  Container(
                    width: 36,
                    height: 36,
                    decoration: BoxDecoration(
                      color: AppColors.orange.withOpacity(0.15),
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: const Icon(
                      Icons.location_on,
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
                          'Local activo',
                          style: Theme.of(context).textTheme.bodySmall?.copyWith(
                                color: AppColors.mediumGrey,
                              ),
                        ),
                        const SizedBox(height: 2),

                        // 🔥 AQUÍ ESTÁ EL CAMBIO
                        Row(
                          children: [
                            Expanded(
                              child: Text(
                                'Vicuña Mackenna, La Florida',
                                style: Theme.of(context).textTheme.titleMedium?.copyWith(
                                      color: AppColors.white,
                                      fontSize: 14,
                                    ),
                              ),
                            ),
                            TextButton(
                              onPressed: () {
                                Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                    builder: (context) => MapRealPage(
                                      lat: -33.4489,
                                      lng: -70.6693,
                                      nombre: "Local Central",
                                    ),
                                  ),
                                );
                              },
                              child: const Text(
                                "Ver mapa",
                                style: TextStyle(color: Colors.orange),
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),

            const SizedBox(height: 24),

            Text(
              '¿Qué quieres hacer?',
              style: Theme.of(context).textTheme.titleLarge,
            ),

            const SizedBox(height: 16),

            _ActionCard(
              icon: Icons.qr_code_scanner,
              title: 'Escanear producto',
              subtitle: 'Apunta la cámara al código de barras',
              color: AppColors.black,
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const ScannerPage(),
                  ),
                );
              },
            ),

            const SizedBox(height: 12),

            _ActionCard(
              icon: Icons.search,
              title: 'Buscar producto',
              subtitle: 'Encuentra productos por nombre',
              color: AppColors.orange,
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const SearchPage(),
                  ),
                );
              },
            ),

            const SizedBox(height: 12),

            _ActionCard(
              icon: Icons.map_outlined,
              title: 'Ver mapa del local',
              subtitle: 'Explora la distribución del supermercado',
              color: AppColors.darkGrey,
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const MapOverviewPage(),
                  ),
                );
              },
            ),

            const SizedBox(height: 24),
          ],
        ),
      ),
    );
  }
}

class _ActionCard extends StatelessWidget {
  final IconData icon;
  final String title;
  final String subtitle;
  final Color color;
  final VoidCallback onTap;

  const _ActionCard({
    required this.icon,
    required this.title,
    required this.subtitle,
    required this.color,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
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
        child: Row(
          children: [
            Container(
              width: 48,
              height: 48,
              decoration: BoxDecoration(
                color: color,
                borderRadius: BorderRadius.circular(14),
              ),
              child: Icon(icon, color: AppColors.white, size: 24),
            ),
            const SizedBox(width: 16),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(title, style: Theme.of(context).textTheme.titleMedium),
                  const SizedBox(height: 2),
                  Text(subtitle, style: Theme.of(context).textTheme.bodySmall),
                ],
              ),
            ),
            const Icon(
              Icons.arrow_forward_ios,
              size: 14,
              color: AppColors.mediumGrey,
            ),
          ],
        ),
      ),
    );
  }
}
