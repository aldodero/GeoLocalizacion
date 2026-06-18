import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../../../service/producto_local_service.dart';

class ProductosLocalPage extends StatefulWidget {
  final int idLocal;
  final String nombreLocal;

  const ProductosLocalPage({
    super.key,
    required this.idLocal,
    required this.nombreLocal,
  });

  @override
  State<ProductosLocalPage> createState() => _ProductosLocalPageState();
}

class _ProductosLocalPageState extends State<ProductosLocalPage> {
  final ProductoLocalService productoLocalService = ProductoLocalService();
  
  List<dynamic> productos = [];
  bool isLoading = true;
  String? error;

  @override
  void initState() {
    super.initState();
    cargarProductos();
  }

  Future<void> cargarProductos() async {
    try {
      setState(() {
        isLoading = true;
        error = null;
      });

      final data = await productoLocalService.obtenerProductosLocal(widget.idLocal);
      
      setState(() {
        productos = data;
        isLoading = false;
      });
      
      // Si no hay productos pero tampoco hay error, mostrar mensaje informativo
      if (data.isEmpty) {
        print("No se encontraron productos para el local ${widget.idLocal}");
      }
      
    } catch (e) {
      print("Error al cargar productos: $e");
      setState(() {
        error = 'Error al cargar productos: $e';
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        backgroundColor: AppColors.white,
        elevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: AppColors.black),
          onPressed: () => Navigator.pop(context),
        ),
        title: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Productos disponibles',
              style: Theme.of(context).textTheme.titleMedium?.copyWith(
                color: AppColors.black,
                fontSize: 16,
                fontWeight: FontWeight.w600,
              ),
            ),
            Text(
              widget.nombreLocal,
              style: Theme.of(context).textTheme.bodySmall?.copyWith(
                color: AppColors.mediumGrey,
                fontSize: 12,
              ),
            ),
          ],
        ),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh, color: AppColors.black),
            onPressed: cargarProductos,
          ),
        ],
      ),
      body: _buildBody(),
    );
  }

  Widget _buildBody() {
    if (isLoading) {
      return const Center(
        child: CircularProgressIndicator(
          color: AppColors.orange,
        ),
      );
    }

    if (error != null) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.error_outline,
              size: 64,
              color: AppColors.mediumGrey,
            ),
            const SizedBox(height: 16),
            Text(
              'Error al cargar productos',
              style: Theme.of(context).textTheme.titleMedium?.copyWith(
                color: AppColors.darkGrey,
              ),
            ),
            const SizedBox(height: 8),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 32),
              child: Text(
                error!,
                textAlign: TextAlign.center,
                style: Theme.of(context).textTheme.bodySmall?.copyWith(
                  color: AppColors.mediumGrey,
                ),
              ),
            ),
            const SizedBox(height: 24),
            ElevatedButton(
              onPressed: cargarProductos,
              style: ElevatedButton.styleFrom(
                backgroundColor: AppColors.orange,
                foregroundColor: AppColors.white,
              ),
              child: const Text('Reintentar'),
            ),
          ],
        ),
      );
    }

    if (productos.isEmpty) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.inventory_2_outlined,
              size: 64,
              color: AppColors.mediumGrey,
            ),
            const SizedBox(height: 16),
            Text(
              'No hay productos disponibles',
              style: Theme.of(context).textTheme.titleMedium?.copyWith(
                color: AppColors.darkGrey,
              ),
            ),
            const SizedBox(height: 8),
            Text(
              'Este local no tiene productos registrados',
              style: Theme.of(context).textTheme.bodySmall?.copyWith(
                color: AppColors.mediumGrey,
              ),
            ),
          ],
        ),
      );
    }

    return RefreshIndicator(
      color: AppColors.orange,
      onRefresh: cargarProductos,
      child: ListView.builder(
        padding: const EdgeInsets.all(16),
        itemCount: productos.length,
        itemBuilder: (context, index) {
          final producto = productos[index];
          return _ProductoCard(producto: producto);
        },
      ),
    );
  }
}

class _ProductoCard extends StatelessWidget {
  final Map<String, dynamic> producto;

  const _ProductoCard({required this.producto});

  @override
  Widget build(BuildContext context) {
    final stock = (producto['stock'] as num?)?.toInt() ?? 0;
    final precio = (producto['precio'] as num?)?.toDouble() ?? 0;
    final estado = producto['estado']?.toString() ?? 'Desconocido';
    final idProducto = producto['idProducto']?.toString() ?? 'N/A';
    final nombreProducto = producto['nombreProducto']?.toString() ?? 'Producto sin nombre';
    final codigoProducto = producto['codigoProducto']?.toString() ?? 'sin codigo';
    final tipo = producto['tipo']?.toString() ?? 'sin tipo';

    // Determinar color del estado
    Color estadoColor = AppColors.mediumGrey;
    if (estado.toLowerCase() == 'disponible') {
      estadoColor = Colors.green;
    } else if (estado.toLowerCase() == 'agotado') {
      estadoColor = Colors.red;
    } else if (estado.toLowerCase() == 'poco stock') {
      estadoColor = Colors.orange;
    }

    Color stockColor;
    if (stock <= 5) {
      stockColor = Colors.red;
    } else if (stock <= 20) {
      stockColor = Colors.orange;
    } else {
      stockColor = Colors.green;
    }

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
            width: 48,
            height: 48,
            decoration: BoxDecoration(
              color: AppColors.orange.withOpacity(0.1),
              borderRadius: BorderRadius.circular(12),
            ),
            child: const Icon(
              Icons.inventory_2,
              color: AppColors.orange,
              size: 24,
            ),
          ),
          const SizedBox(width: 16),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  nombreProducto,
                  style: Theme.of(context).textTheme.titleMedium?.copyWith(
                    fontSize: 14,
                    fontWeight: FontWeight.w600,
                  ),
                ),
                const SizedBox(height: 4),
                Text(
                  'Código: $codigoProducto',
                  style: Theme.of(context).textTheme.bodySmall?.copyWith(
                    color: AppColors.mediumGrey,
                    fontSize: 12,
                  ),
                ),
                const SizedBox(height: 4),
                Text(
                  tipo,
                  style: Theme.of(context).textTheme.bodySmall?.copyWith(
                    color: AppColors.mediumGrey,
                    fontSize: 12,
                  ),
                ),
                const SizedBox(height: 8),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Container(
                      padding: const EdgeInsets.symmetric(
                        horizontal: 10,
                        vertical: 4,
                      ),
                      decoration: BoxDecoration(
                        color: Colors.blue.withOpacity(0.15),
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: Text(
                        'Precio fleje: \$${precio.toStringAsFixed(0)}',
                        style: const TextStyle(
                          color: Colors.blue,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                    Container(
                      padding: const EdgeInsets.symmetric(
                        horizontal: 10,
                        vertical: 4,
                      ),
                      decoration: BoxDecoration(
                        color: stockColor,
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: Text(
                        'Stock: $stock',
                        style: const TextStyle(
                          color: Colors.white,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}