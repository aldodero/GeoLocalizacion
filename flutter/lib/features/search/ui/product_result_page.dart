import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../../map/ui/map_page.dart';
import '../../../service/favoritos_service.dart';

class ProductResultPage extends StatefulWidget {
  final int idProducto;
  final int idLocal;
  final String nombre;
  final String codigo;
  final String ubicacion;
  final double productX;
  final double productY;
  final String modo;

  const ProductResultPage({
    super.key,
    required this.idProducto,
    required this.idLocal,
    required this.nombre,
    required this.modo,
    this.codigo = "7801234567890",
    this.ubicacion = "Pasillo 5 - Bebidas - B1-B11",
    this.productX = 150,
    this.productY = 200,
  });

  @override
  State<ProductResultPage> createState() => _ProductResultPageState();
}

class _ProductResultPageState extends State<ProductResultPage> {
  final FavoritosService favoritosService = FavoritosService();
  bool esFavorito = false;
  bool cargando = true;

  @override
  void initState() {
    super.initState();
    verificarFavorito();
  }

  Future<void> verificarFavorito() async {
    final resultado = await favoritosService.esFavorito(widget.codigo);
    setState(() {
      esFavorito = resultado;
      cargando = false;
    });
  }

  Future<void> toggleFavorito() async {
    if (esFavorito) {
      // TODO: implementar eliminar favorito cuando el backend lo soporte
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Producto ya está en favoritos")),
      );
      return;
    }

    try {
      await favoritosService.guardarFavorito({
        "idUsuario" : 1,
        "idLocal": widget.idLocal,
        "idProducto": widget.idProducto,
      });

      setState(() => esFavorito = true);

      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text("Producto agregado a favoritos"),
            backgroundColor: AppColors.orange,
            duration: Duration(seconds: 2),
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("Error al agregar a favoritos")),
        );
      }
    }
  }

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
                  // ÍCONO + NOMBRE + FAVORITO
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
                          widget.nombre,
                          style: Theme.of(context).textTheme.titleLarge,
                        ),
                      ),
                      // ÍCONO FAVORITO
                      cargando
                          ? const SizedBox(
                              width: 24,
                              height: 24,
                              child: CircularProgressIndicator(
                                strokeWidth: 2,
                                color: AppColors.orange,
                              ),
                            )
                          : IconButton(
                              onPressed: toggleFavorito,
                              icon: Icon(
                                esFavorito
                                    ? Icons.favorite
                                    : Icons.favorite_border,
                                color: AppColors.orange,
                                size: 28,
                              ),
                              padding: EdgeInsets.zero,
                              constraints: const BoxConstraints(),
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
                    value: widget.codigo,
                  ),

                  const SizedBox(height: 12),

                  // UBICACIÓN
                  _InfoRow(
                    icon: Icons.location_on_outlined,
                    label: 'Ubicación',
                    value: widget.ubicacion,
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
                        productX: widget.productX,
                        productY: widget.productY,
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
                icon: Icon(
                  widget.modo == "scanner"
                      ? Icons.qr_code_scanner
                      : widget.modo == "favoritos"
                          ? Icons.star
                          : Icons.search,
                  size: 20,
                ),
                label: Text(
                  widget.modo == "scanner"
                      ? "Escanear otro producto"
                      : widget.modo == "favoritos"
                          ? "Volver a favoritos"
                          : "Buscar otro producto",
                ),
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
