import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../../search/ui/product_result_page.dart';
import '../../../service/product_service.dart';





class ManualCodePage extends StatefulWidget {
  final int idLocal;
  
  const ManualCodePage({
    super.key,
    required this.idLocal,
  });

  @override
  State<ManualCodePage> createState() => _ManualCodePageState();
}

class _ManualCodePageState extends State<ManualCodePage> {

  final TextEditingController controller =
      TextEditingController();

  final ProductService productService =
      ProductService();


  //METODO BUSCAR
  Future<void> buscar() async {
    //validar codigo 13 digitos
     final codigo = controller.text.trim();

  if (!RegExp(r'^\d{13}$').hasMatch(codigo)) {

    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(
        content: Text(
          "Ingrese un código válido de 13 dígitos",
        ),
      ),
    );
    return;
  }

    try {

      final producto =

          await productService.buscarPorCodigo(
        controller.text,
        widget.idLocal,
      );
      print("PRODUCTO COMPLETO");
      print(producto);

      if (!mounted) return;

      Navigator.pushReplacement(
        context,
        MaterialPageRoute(
          builder: (context) => ProductResultPage(
            idProducto: producto["idProducto"],
            idLocal: widget.idLocal,
            modo: "scanner",
            nombre: producto["nombreProducto"],
            codigo: producto["codigoProducto"],
            ubicacion: producto["ubicacion"],
            productX: 120,
            productY: 180,
          ),
        ),
      );

    } catch (e) {

      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text(
            "Producto no encontrado",
          ),
        ),
      );
    }
  }
//fin 
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        title: const Text('Código manual'),
        leading: IconButton(
          icon: const Icon(
            Icons.arrow_back_ios_new,
            size: 18,
          ),
          onPressed: () => Navigator.pop(context),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment:
              CrossAxisAlignment.start,
          children: [

            Container(
              width: double.infinity,
              padding: const EdgeInsets.all(20),
              decoration: BoxDecoration(
                color: AppColors.white,
                borderRadius:
                    BorderRadius.circular(20),
                boxShadow: [
                  BoxShadow(
                    color: AppColors.black
                        .withOpacity(0.06),
                    blurRadius: 16,
                    offset: const Offset(0, 4),
                  ),
                ],
              ),
              child: Column(
                crossAxisAlignment:
                    CrossAxisAlignment.start,
                children: [
                  Text(
                    'Código de barras',
                    style: Theme.of(context)
                        .textTheme
                        .titleMedium,
                  ),
                  const SizedBox(height: 4),
                  Text(
                    'Ingresa los 13 dígitos del código',
                    style: Theme.of(context)
                        .textTheme
                        .bodySmall,
                  ),
                  const SizedBox(height: 16),
                  TextField(
                    controller: controller,
                    keyboardType:
                        TextInputType.number,
                    maxLength: 13,
                    decoration:
                        const InputDecoration(
                      hintText:
                          '0000000000000',
                      prefixIcon:
                          Icon(Icons.qr_code),
                      counterText: '',
                    ),
                  ),
                ],
              ),
            ),

            const SizedBox(height: 24),

            SizedBox(
              width: double.infinity,
              child: ElevatedButton.icon(
                onPressed: buscar,
                icon: const Icon(
                  Icons.search,
                  size: 20,
                ),
                label: const Text(
                  'Buscar producto',
                ),
                style:
                    ElevatedButton.styleFrom(
                  padding:
                      const EdgeInsets.symmetric(
                    vertical: 16,
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}