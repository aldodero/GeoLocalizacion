import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../../home/ui/home_page.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController usuarioController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  bool ocultarPassword = true;

  @override
  void dispose() {
    usuarioController.dispose();
    passwordController.dispose();
    super.dispose();
  }

  void login() {
    String usuario = usuarioController.text;
    String password = passwordController.text;

    if (usuario.isEmpty || password.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Completa todos los campos")),
      );
      return;
    }

    // 👉 Aquí luego conectamos backend

    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => const HomePage()),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 32),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              const SizedBox(height: 24),

              // LOGO
              Container(
                width: 88,
                height: 88,
                decoration: BoxDecoration(
                  color: AppColors.black,
                  borderRadius: BorderRadius.circular(24),
                  boxShadow: [
                    BoxShadow(
                      color: AppColors.black.withOpacity(0.18),
                      blurRadius: 16,
                      offset: const Offset(0, 6),
                    ),
                  ],
                ),
                child: const Icon(
                  Icons.location_on,
                  color: AppColors.orange,
                  size: 44,
                ),
              ),

              const SizedBox(height: 24),

              // TÍTULO
              Text(
                'GeoMarket',
                style: Theme.of(context).textTheme.headlineMedium,
              ),

              const SizedBox(height: 8),

              Text(
                'Ingresa tus datos para continuar',
                style: Theme.of(context).textTheme.bodyMedium,
              ),

              const SizedBox(height: 40),

              // CARD DE FORMULARIO
              Container(
                padding: const EdgeInsets.all(24),
                decoration: BoxDecoration(
                  color: AppColors.white,
                  borderRadius: BorderRadius.circular(20),
                  boxShadow: [
                    BoxShadow(
                      color: AppColors.black.withOpacity(0.07),
                      blurRadius: 20,
                      offset: const Offset(0, 4),
                    ),
                  ],
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    // USUARIO
                    Text(
                      'Usuario',
                      style: Theme.of(context).textTheme.labelLarge,
                    ),
                    const SizedBox(height: 8),
                    TextField(
                      controller: usuarioController,
                      decoration: const InputDecoration(
                        hintText: 'Ingresa tu usuario',
                        prefixIcon: Icon(Icons.person_outline),
                      ),
                    ),

                    const SizedBox(height: 20),

                    // PASSWORD
                    Text(
                      'Contraseña',
                      style: Theme.of(context).textTheme.labelLarge,
                    ),
                    const SizedBox(height: 8),
                    TextField(
                      controller: passwordController,
                      obscureText: ocultarPassword,
                      decoration: InputDecoration(
                        hintText: 'Ingresa tu contraseña',
                        prefixIcon: const Icon(Icons.lock_outline),
                        suffixIcon: IconButton(
                          icon: Icon(
                            ocultarPassword
                                ? Icons.visibility_outlined
                                : Icons.visibility_off_outlined,
                          ),
                          onPressed: () {
                            setState(() {
                              ocultarPassword = !ocultarPassword;
                            });
                          },
                        ),
                      ),
                    ),

                    const SizedBox(height: 12),

                    // RECUPERAR CONTRASEÑA
                    Align(
                      alignment: Alignment.centerRight,
                      child: GestureDetector(
                        onTap: () {},
                        child: Text(
                          'Recuperar contraseña',
                          style: Theme.of(context).textTheme.bodySmall?.copyWith(
                                color: AppColors.orange,
                                fontWeight: FontWeight.w600,
                              ),
                        ),
                      ),
                    ),

                    const SizedBox(height: 28),

                    // BOTÓN LOGIN
                    SizedBox(
                      width: double.infinity,
                      child: ElevatedButton(
                        onPressed: login,
                        style: ElevatedButton.styleFrom(
                          backgroundColor: AppColors.orange,
                          foregroundColor: AppColors.white,
                          padding: const EdgeInsets.symmetric(vertical: 16),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(12),
                          ),
                        ),
                        child: const Text(
                          'Acceder',
                          style: TextStyle(
                            fontSize: 15,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),

              const SizedBox(height: 32),

              // AYUDA
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Icon(
                    Icons.help_outline,
                    size: 16,
                    color: AppColors.mediumGrey,
                  ),
                  const SizedBox(width: 6),
                  Text(
                    'Centro de ayuda',
                    style: Theme.of(context).textTheme.bodySmall,
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
