import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import 'product_result_page.dart';
import '../../../service/product_service.dart';
import '../../../service/historial_service.dart';


class SearchPage extends StatefulWidget {
  const SearchPage({super.key});

  @override
  State<SearchPage> createState() => _SearchPageState();
}

class _SearchPageState extends State<SearchPage> {
  final TextEditingController searchController = TextEditingController();
  final HistorialService historialService = HistorialService();
  final ProductService service = ProductService();

  List<dynamic> productos = [];
  bool cargando = false;

  // 🔥 BUSCAR EN BACKEND
  Future<void> buscarProductos(String query) async {
    if (query.trim().isEmpty) {
      setState(() => productos = []);
      return;
    }

    setState(() => cargando = true);

    try {
      final resultado = await service.buscarProductos(query);
      setState(() => productos = resultado);
    } catch (e) {
      print("ERROR BACKEND: $e");
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Error al conectar con el servidor")),
      );
    }

    setState(() => cargando = false);
  }

  void seleccionarSugerencia(String texto) {
    searchController.text = texto;
    buscarProductos(texto);
  }

  @override
  void dispose() {
    searchController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        title: const Text('Buscar producto'),
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
            // BUSCADOR
            TextField(
              controller: searchController,
              onChanged: buscarProductos,
              decoration: const InputDecoration(
                hintText: 'Buscar producto...',
                prefixIcon: Icon(Icons.search),
              ),
            ),

            const SizedBox(height: 16),

            // SUGERENCIAS
            Text(
              'Búsquedas rápidas',
              style: Theme.of(context).textTheme.bodySmall,
            ),
            const SizedBox(height: 8),
            Row(
              children: [
                _SuggestionChip(
                  label: 'Coca-Cola',
                  onTap: () => seleccionarSugerencia('coca'),
                ),
                const SizedBox(width: 8),
                _SuggestionChip(
                  label: 'Jugo',
                  onTap: () => seleccionarSugerencia('jugo'),
                ),
              ],
            ),

            const SizedBox(height: 20),

            // RESULTADOS
            Expanded(
              child: cargando
                  ? const Center(
                      child: CircularProgressIndicator(
                        color: AppColors.orange,
                      ),
                    )
                  : productos.isEmpty
                      ? Center(
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              const Icon(
                                Icons.search_off,
                                size: 56,
                                color: AppColors.mediumGrey,
                              ),
                              const SizedBox(height: 12),
                              Text(
                                'Sin resultados',
                                style: Theme.of(context).textTheme.titleMedium,
                              ),
                              const SizedBox(height: 4),
                              Text(
                                'Intenta con otro término',
                                style: Theme.of(context).textTheme.bodySmall,
                              ),
                            ],
                          ),
                        )
                      : ListView.builder(
                          itemCount: productos.length,
                          itemBuilder: (context, index) {
                            final producto = productos[index];
                            return _ProductCard(
                              nombre: producto["nombre"] ?? "Sin nombre",
                              pasillo: producto["pasillo"]?.toString() ?? "-",
                              ubicacion: producto["ubicacion"] ?? "",

                              onTap: ()async{
                                print(producto);
                                await historialService.guardarHistorial({
                                "nombreProducto": producto["nombre"],
                                "codigoProducto": producto["codigo"],
                                "tipo": producto["tipo"],
                                "nombreLocal": producto["nombreLocal"],
                              });
                                  Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                    builder: (context) => ProductResultPage(
                                      nombre: producto["nombre"] ?? "",
                                      codigo: producto["codigo"] ?? "",
                                      ubicacion: producto["tipo"] ?? "",
                                      productX: (producto["x"] ?? 150).toDouble(),
                                      productY: (producto["y"] ?? 200).toDouble(),
                                    ),
                                  ),
                                );
                              },
                            );
                          },
                      ),
            ),
          ],
        ),
      ),
    );
  }
}

class _SuggestionChip extends StatelessWidget {
  final String label;
  final VoidCallback onTap;

  const _SuggestionChip({required this.label, required this.onTap});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 8),
        decoration: BoxDecoration(
          color: AppColors.white,
          borderRadius: BorderRadius.circular(20),
          border: Border.all(color: const Color(0xFFE0E0E0)),
          boxShadow: [
            BoxShadow(
              color: AppColors.black.withOpacity(0.04),
              blurRadius: 6,
              offset: const Offset(0, 2),
            ),
          ],
        ),
        child: Text(
          label,
          style: Theme.of(context).textTheme.bodySmall?.copyWith(
                fontWeight: FontWeight.w600,
                color: AppColors.black,
              ),
        ),
      ),
    );
  }
}

class _ProductCard extends StatelessWidget {
  final String nombre;
  final String pasillo;
  final String ubicacion;
  final VoidCallback onTap;

  const _ProductCard({
    required this.nombre,
    required this.pasillo,
    required this.ubicacion,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        margin: const EdgeInsets.only(bottom: 10),
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
            Container(
              width: 44,
              height: 44,
              decoration: BoxDecoration(
                color: AppColors.orange.withOpacity(0.1),
                borderRadius: BorderRadius.circular(12),
              ),
              child: const Icon(
                Icons.inventory_2_outlined,
                color: AppColors.orange,
                size: 22,
              ),
            ),
            const SizedBox(width: 14),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    nombre,
                    style: Theme.of(context).textTheme.titleMedium,
                  ),
                  const SizedBox(height: 3),
                  Text(
                    'Pasillo $pasillo · $ubicacion',
                    style: Theme.of(context).textTheme.bodySmall,
                  ),
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
