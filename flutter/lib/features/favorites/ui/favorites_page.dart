import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../../../service/favoritos_service.dart';
import '../../search/ui/product_result_page.dart';

class FavoritesPage extends StatefulWidget {
  final int idLocal;
  const FavoritesPage({super.key, required this.idLocal});


  @override
  State<FavoritesPage> createState() => _FavoritesPageState();
}


class _FavoritesPageState extends State<FavoritesPage> {
  final FavoritosService favoritosService = FavoritosService();
  List<dynamic> favoritos = [];
  bool cargando = true;

  @override
  void initState() {
    super.initState();
    cargarFavoritos();
  }

  Future<void> cargarFavoritos() async {
    final data = await favoritosService.obtenerFavoritosPorLocal(
      1,
      widget.idLocal,
    );

    setState(() {
      favoritos = data;
      cargando = false;
    });
  }




  Future<void> confirmarEliminar(int idUsuario, int idProducto, String nombreProducto) async {
    final confirmar = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(16),
        ),
        title: const Text('¿Eliminar favorito?'),
        content: Text(
          '¿Deseas eliminar "$nombreProducto" de tus favoritos?',
          style: Theme.of(context).textTheme.bodyMedium,
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: const Text(
              'Cancelar',
              style: TextStyle(color: AppColors.mediumGrey),
            ),
          ),
          ElevatedButton(
            onPressed: () => Navigator.pop(context, true),
            style: ElevatedButton.styleFrom(
              backgroundColor: AppColors.orange,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            child: const Text('Eliminar'),
          ),
        ],
      ),
    );

    if (confirmar == true) {
      await eliminarFavorito(idUsuario, idProducto);
    }
  }





  Future<void> eliminarFavorito(int idUsuario, int idProducto) async {
    try {
      await favoritosService.eliminarFavorito(idUsuario, idProducto);

      // Recargar lista completa desde el backend
      await cargarFavoritos();

      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text("Favorito eliminado"),
            backgroundColor: AppColors.orange,
            duration: Duration(seconds: 2),
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("Error al eliminar favorito")),
        );
      }
    }
  }




  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        title: const Text('Favoritos'),
        automaticallyImplyLeading: false,
      ),
      body: cargando
          ? const Center(child: CircularProgressIndicator())
          : favoritos.isEmpty
              ? _emptyState(context)
              : _list(context),
    );
  }

  Widget _list(BuildContext context) {
    return ListView.builder(
      padding: const EdgeInsets.all(16),
      itemCount: favoritos.length,
      itemBuilder: (context, index) {
        final item = favoritos[index];
        return _FavoriteCard(
          nombre: item["nombreProducto"] ?? "Sin nombre",
          codigo: item["codigoProducto"] ?? "",
          ubicacion: item["tipo"] ?? "",
          onTap: () {

            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => ProductResultPage(
                  idProducto: item["idProducto"] ?? 0,
                  idLocal: widget.idLocal,
                  modo: "favoritos",
                  nombre: item["nombreProducto"] ?? "",
                  codigo: item["codigoProducto"] ?? "",
                  ubicacion: item["tipo"] ?? "",
                  productX: (item["x"] ?? 150).toDouble(),
                  productY: (item["y"] ?? 200).toDouble(),
                ),
              ),
            );
          },

          onDelete: () {
            confirmarEliminar(
              item["idUsuario"] ?? 1,
              item["idProducto"] ?? 0,
              item["nombreProducto"] ?? "este producto",
            );
          },
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
  final VoidCallback onTap;
  final VoidCallback onDelete;

  const _FavoriteCard({
    required this.nombre,
    required this.codigo,
    required this.ubicacion,
    required this.onTap,
    required this.onDelete,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
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
          // ÍCONO ESTRELLA (CLICKEABLE)
          GestureDetector(
            onTap: onDelete,
            child: Container(
              width: 44,
              height: 44,
              decoration: BoxDecoration(
                color: AppColors.orange.withOpacity(0.1),
                borderRadius: BorderRadius.circular(12),
              ),
              child: const Icon(Icons.star, color: AppColors.orange, size: 22),
            ),
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
            onPressed: onTap,
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
            child: const Text('Ver'),
          ),
        ],
      ),
    ),
    );
  }
}
