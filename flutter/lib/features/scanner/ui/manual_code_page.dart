import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../../search/ui/product_result_page.dart';

class ManualCodePage extends StatefulWidget {
  const ManualCodePage({super.key});

  @override
  State<ManualCodePage> createState() => _ManualCodePageState();
}

class _ManualCodePageState extends State<ManualCodePage> {
  final TextEditingController controller = TextEditingController();

  void buscar() {
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(
        builder: (context) => ProductResultPage(
          nombre: "Producto manual",
          codigo: controller.text,
          ubicacion: "Pasillo 3 - Snacks",
          productX: 120,
          productY: 180,
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        title: const Text('Código manual'),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios_new, size: 18),
          onPressed: () => Navigator.pop(context),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // CARD
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
                  Text(
                    'Código de barras',
                    style: Theme.of(context).textTheme.titleMedium,
                  ),
                  const SizedBox(height: 4),
                  Text(
                    'Ingresa los 13 dígitos del código',
                    style: Theme.of(context).textTheme.bodySmall,
                  ),
                  const SizedBox(height: 16),
                  TextField(
                    controller: controller,
                    keyboardType: TextInputType.number,
                    maxLength: 13,
                    decoration: const InputDecoration(
                      hintText: '0000000000000',
                      prefixIcon: Icon(Icons.qr_code),
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
                icon: const Icon(Icons.search, size: 20),
                label: const Text('Buscar producto'),
                style: ElevatedButton.styleFrom(
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
