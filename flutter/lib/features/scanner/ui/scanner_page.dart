import 'package:flutter/material.dart';
import 'package:mobile_scanner/mobile_scanner.dart';
import '../../../core/theme/app_theme.dart';
import '../../search/ui/product_result_page.dart';
import 'manual_code_page.dart';

class ScannerPage extends StatefulWidget {
  const ScannerPage({super.key});

  @override
  State<ScannerPage> createState() => _ScannerPageState();
}

class _ScannerPageState extends State<ScannerPage>
    with SingleTickerProviderStateMixin {
  late AnimationController _animController;
  late Animation<double> _scanAnimation;

  @override
  void initState() {
    super.initState();
    _animController = AnimationController(
      vsync: this,
      duration: const Duration(seconds: 2),
    )..repeat(reverse: true);

    _scanAnimation = Tween<double>(begin: 0, end: 1).animate(
      CurvedAnimation(parent: _animController, curve: Curves.easeInOut),
    );
  }

  @override
  void dispose() {
    _animController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.black,
      appBar: AppBar(
        title: const Text('Escanear producto'),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios_new, size: 18),
          onPressed: () => Navigator.pop(context),
        ),
      ),
      body: Column(
        children: [
          // SCANNER FULLSCREEN
          Expanded(
            child: Stack(
              children: [
                // CÁMARA
                MobileScanner(
                  onDetect: (barcodeCapture) {
                    final List<Barcode> barcodes = barcodeCapture.barcodes;
                    if (barcodes.isEmpty) return;
                    final String? code = barcodes.first.rawValue;
                    if (code == null) return;

                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                        builder: (context) => ProductResultPage(
                          nombre: "Producto escaneado",
                          codigo: code,
                          ubicacion: "Pasillo 5 - Bebidas",
                          productX: 150,
                          productY: 200,
                        ),
                      ),
                    );
                  },
                ),

                // OVERLAY OSCURO (4 esquinas)
                _buildOverlay(),

                // MARCO DE ESCANEO + LÍNEA ANIMADA
                Center(
                  child: SizedBox(
                    width: 240,
                    height: 240,
                    child: Stack(
                      children: [
                        // MARCO
                        Container(
                          decoration: BoxDecoration(
                            border: Border.all(
                              color: AppColors.orange,
                              width: 2.5,
                            ),
                            borderRadius: BorderRadius.circular(16),
                          ),
                        ),

                        // LÍNEA ANIMADA
                        AnimatedBuilder(
                          animation: _scanAnimation,
                          builder: (context, child) {
                            return Positioned(
                              top: _scanAnimation.value * 220,
                              left: 8,
                              right: 8,
                              child: Container(
                                height: 2,
                                decoration: BoxDecoration(
                                  gradient: LinearGradient(
                                    colors: [
                                      AppColors.orange.withOpacity(0),
                                      AppColors.orange,
                                      AppColors.orange.withOpacity(0),
                                    ],
                                  ),
                                  borderRadius: BorderRadius.circular(2),
                                ),
                              ),
                            );
                          },
                        ),
                      ],
                    ),
                  ),
                ),

                // TEXTO GUÍA
                Positioned(
                  bottom: 32,
                  left: 0,
                  right: 0,
                  child: Center(
                    child: Container(
                      padding: const EdgeInsets.symmetric(
                        horizontal: 16,
                        vertical: 8,
                      ),
                      decoration: BoxDecoration(
                        color: AppColors.black.withOpacity(0.6),
                        borderRadius: BorderRadius.circular(20),
                      ),
                      child: const Text(
                        'Apunta al código de barras',
                        style: TextStyle(
                          color: AppColors.white,
                          fontSize: 13,
                        ),
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),

          // BOTÓN CÓDIGO MANUAL
          Container(
            color: AppColors.black,
            padding: const EdgeInsets.fromLTRB(16, 12, 16, 24),
            child: SizedBox(
              width: double.infinity,
              child: OutlinedButton.icon(
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => const ManualCodePage(),
                    ),
                  );
                },
                icon: const Icon(Icons.keyboard_outlined, size: 18),
                label: const Text('Ingresar código manual'),
                style: OutlinedButton.styleFrom(
                  foregroundColor: AppColors.white,
                  side: const BorderSide(color: AppColors.white, width: 1.5),
                  padding: const EdgeInsets.symmetric(vertical: 14),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildOverlay() {
    return LayoutBuilder(
      builder: (context, constraints) {
        const double frameSize = 240;
        final double centerX = constraints.maxWidth / 2;
        final double centerY = constraints.maxHeight / 2;
        final double left = centerX - frameSize / 2;
        final double top = centerY - frameSize / 2;

        return Stack(
          children: [
            // TOP
            Positioned(
              top: 0,
              left: 0,
              right: 0,
              height: top,
              child: Container(color: Colors.black.withOpacity(0.6)),
            ),
            // BOTTOM
            Positioned(
              top: top + frameSize,
              left: 0,
              right: 0,
              bottom: 0,
              child: Container(color: Colors.black.withOpacity(0.6)),
            ),
            // LEFT
            Positioned(
              top: top,
              left: 0,
              width: left,
              height: frameSize,
              child: Container(color: Colors.black.withOpacity(0.6)),
            ),
            // RIGHT
            Positioned(
              top: top,
              left: left + frameSize,
              right: 0,
              height: frameSize,
              child: Container(color: Colors.black.withOpacity(0.6)),
            ),
          ],
        );
      },
    );
  }
}
