import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';

class SettingsPage extends StatefulWidget {
  const SettingsPage({super.key});

  @override
  State<SettingsPage> createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  // Estados locales para switches
  bool temaDarkMode = false;
  bool notificacionesActivas = true;
  bool sonidosActivos = true;
  bool vibracionActiva = true;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        title: const Text("Configuración"),
        backgroundColor: AppColors.white,
        elevation: 0,
        iconTheme: const IconThemeData(color: AppColors.black),
        titleTextStyle: const TextStyle(
          color: AppColors.black,
          fontSize: 18,
          fontWeight: FontWeight.w600,
        ),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // SECCIÓN: APARIENCIA
            _SeccionHeader(
              icon: Icons.palette_outlined,
              titulo: "Apariencia",
            ),
            const SizedBox(height: 12),
            _SettingsCard(
              children: [
                SwitchListTile(
                  value: temaDarkMode,
                  onChanged: (value) {
                    setState(() => temaDarkMode = value);
                  },
                  title: const Text(
                    "Tema oscuro",
                    style: TextStyle(
                      fontSize: 15,
                      fontWeight: FontWeight.w500,
                      color: AppColors.black,
                    ),
                  ),
                  subtitle: const Text(
                    "Activar modo nocturno",
                    style: TextStyle(
                      fontSize: 13,
                      color: AppColors.mediumGrey,
                    ),
                  ),
                  secondary: Container(
                    width: 40,
                    height: 40,
                    decoration: BoxDecoration(
                      color: AppColors.orange.withOpacity(0.1),
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: const Icon(
                      Icons.dark_mode_outlined,
                      color: AppColors.orange,
                      size: 20,
                    ),
                  ),
                  activeColor: AppColors.orange,
                  contentPadding: const EdgeInsets.symmetric(
                    horizontal: 16,
                    vertical: 4,
                  ),
                ),
                const Divider(height: 1),
                _SettingsTile(
                  icon: Icons.text_fields,
                  titulo: "Tamaño de texto",
                  subtitulo: "Mediano",
                  onTap: () {
                    // Placeholder para futura implementación
                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(
                        content: Text("Próximamente disponible"),
                        backgroundColor: AppColors.orange,
                      ),
                    );
                  },
                ),
                const Divider(height: 1),
                _SettingsTile(
                  icon: Icons.language,
                  titulo: "Idioma",
                  subtitulo: "Español",
                  onTap: () {
                    // Placeholder para futura implementación
                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(
                        content: Text("Próximamente disponible"),
                        backgroundColor: AppColors.orange,
                      ),
                    );
                  },
                ),
              ],
            ),

            const SizedBox(height: 24),

            // SECCIÓN: NOTIFICACIONES
            _SeccionHeader(
              icon: Icons.notifications_outlined,
              titulo: "Notificaciones",
            ),
            const SizedBox(height: 12),
            _SettingsCard(
              children: [
                SwitchListTile(
                  value: notificacionesActivas,
                  onChanged: (value) {
                    setState(() => notificacionesActivas = value);
                  },
                  title: const Text(
                    "Activar notificaciones",
                    style: TextStyle(
                      fontSize: 15,
                      fontWeight: FontWeight.w500,
                      color: AppColors.black,
                    ),
                  ),
                  subtitle: const Text(
                    "Recibir alertas de la app",
                    style: TextStyle(
                      fontSize: 13,
                      color: AppColors.mediumGrey,
                    ),
                  ),
                  secondary: Container(
                    width: 40,
                    height: 40,
                    decoration: BoxDecoration(
                      color: AppColors.orange.withOpacity(0.1),
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: const Icon(
                      Icons.notifications_active_outlined,
                      color: AppColors.orange,
                      size: 20,
                    ),
                  ),
                  activeColor: AppColors.orange,
                  contentPadding: const EdgeInsets.symmetric(
                    horizontal: 16,
                    vertical: 4,
                  ),
                ),
                const Divider(height: 1),
                SwitchListTile(
                  value: sonidosActivos,
                  onChanged: (value) {
                    setState(() => sonidosActivos = value);
                  },
                  title: const Text(
                    "Sonidos",
                    style: TextStyle(
                      fontSize: 15,
                      fontWeight: FontWeight.w500,
                      color: AppColors.black,
                    ),
                  ),
                  subtitle: const Text(
                    "Reproducir sonidos de notificación",
                    style: TextStyle(
                      fontSize: 13,
                      color: AppColors.mediumGrey,
                    ),
                  ),
                  secondary: Container(
                    width: 40,
                    height: 40,
                    decoration: BoxDecoration(
                      color: AppColors.orange.withOpacity(0.1),
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: const Icon(
                      Icons.volume_up_outlined,
                      color: AppColors.orange,
                      size: 20,
                    ),
                  ),
                  activeColor: AppColors.orange,
                  contentPadding: const EdgeInsets.symmetric(
                    horizontal: 16,
                    vertical: 4,
                  ),
                ),
                const Divider(height: 1),
                SwitchListTile(
                  value: vibracionActiva,
                  onChanged: (value) {
                    setState(() => vibracionActiva = value);
                  },
                  title: const Text(
                    "Vibración",
                    style: TextStyle(
                      fontSize: 15,
                      fontWeight: FontWeight.w500,
                      color: AppColors.black,
                    ),
                  ),
                  subtitle: const Text(
                    "Vibrar al recibir notificaciones",
                    style: TextStyle(
                      fontSize: 13,
                      color: AppColors.mediumGrey,
                    ),
                  ),
                  secondary: Container(
                    width: 40,
                    height: 40,
                    decoration: BoxDecoration(
                      color: AppColors.orange.withOpacity(0.1),
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: const Icon(
                      Icons.vibration,
                      color: AppColors.orange,
                      size: 20,
                    ),
                  ),
                  activeColor: AppColors.orange,
                  contentPadding: const EdgeInsets.symmetric(
                    horizontal: 16,
                    vertical: 4,
                  ),
                ),
              ],
            ),

            const SizedBox(height: 24),

            // SECCIÓN: PRIVACIDAD
            _SeccionHeader(
              icon: Icons.privacy_tip_outlined,
              titulo: "Privacidad",
            ),
            const SizedBox(height: 12),
            _SettingsCard(
              children: [
                _SettingsTile(
                  icon: Icons.shield_outlined,
                  titulo: "Política de privacidad",
                  subtitulo: "Ver cómo protegemos tus datos",
                  onTap: () {
                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(
                        content: Text("Próximamente disponible"),
                        backgroundColor: AppColors.orange,
                      ),
                    );
                  },
                ),
                const Divider(height: 1),
                _SettingsTile(
                  icon: Icons.description_outlined,
                  titulo: "Términos y condiciones",
                  subtitulo: "Leer términos de uso",
                  onTap: () {
                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(
                        content: Text("Próximamente disponible"),
                        backgroundColor: AppColors.orange,
                      ),
                    );
                  },
                ),
              ],
            ),

            const SizedBox(height: 24),

            // SECCIÓN: INFORMACIÓN DE LA APP
            _SeccionHeader(
              icon: Icons.info_outline,
              titulo: "Información",
            ),
            const SizedBox(height: 12),
            Container(
              width: double.infinity,
              padding: const EdgeInsets.all(20),
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
              child: Column(
                children: [
                  Container(
                    width: 70,
                    height: 70,
                    decoration: BoxDecoration(
                      color: AppColors.orange.withOpacity(0.15),
                      borderRadius: BorderRadius.circular(20),
                    ),
                    child: const Icon(
                      Icons.store,
                      size: 35,
                      color: AppColors.orange,
                    ),
                  ),
                  const SizedBox(height: 16),
                  const Text(
                    "GeoMarket",
                    style: TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                      color: AppColors.black,
                    ),
                  ),
                  const SizedBox(height: 4),
                  const Text(
                    "v1.0.0",
                    style: TextStyle(
                      fontSize: 13,
                      color: AppColors.mediumGrey,
                      fontWeight: FontWeight.w500,
                    ),
                  ),
                  const SizedBox(height: 12),
                  const Text(
                    "Sistema inteligente de búsqueda y operación para supermercados",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: 14,
                      color: AppColors.darkGrey,
                      height: 1.5,
                    ),
                  ),
                ],
              ),
            ),

            const SizedBox(height: 32),
          ],
        ),
      ),
    );
  }
}

// Widget para headers de sección
class _SeccionHeader extends StatelessWidget {
  final IconData icon;
  final String titulo;

  const _SeccionHeader({
    required this.icon,
    required this.titulo,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Icon(
          icon,
          size: 20,
          color: AppColors.orange,
        ),
        const SizedBox(width: 8),
        Text(
          titulo,
          style: const TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.w700,
            color: AppColors.black,
            letterSpacing: 0.3,
          ),
        ),
      ],
    );
  }
}

// Widget para card contenedor de settings
class _SettingsCard extends StatelessWidget {
  final List<Widget> children;

  const _SettingsCard({required this.children});

  @override
  Widget build(BuildContext context) {
    return Container(
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
      child: Column(
        children: children,
      ),
    );
  }
}

// Widget para tiles individuales con navegación
class _SettingsTile extends StatelessWidget {
  final IconData icon;
  final String titulo;
  final String subtitulo;
  final VoidCallback onTap;

  const _SettingsTile({
    required this.icon,
    required this.titulo,
    required this.subtitulo,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return ListTile(
      onTap: onTap,
      contentPadding: const EdgeInsets.symmetric(
        horizontal: 16,
        vertical: 8,
      ),
      leading: Container(
        width: 40,
        height: 40,
        decoration: BoxDecoration(
          color: AppColors.orange.withOpacity(0.1),
          borderRadius: BorderRadius.circular(10),
        ),
        child: Icon(
          icon,
          color: AppColors.orange,
          size: 20,
        ),
      ),
      title: Text(
        titulo,
        style: const TextStyle(
          fontSize: 15,
          fontWeight: FontWeight.w500,
          color: AppColors.black,
        ),
      ),
      subtitle: Text(
        subtitulo,
        style: const TextStyle(
          fontSize: 13,
          color: AppColors.mediumGrey,
        ),
      ),
      trailing: const Icon(
        Icons.chevron_right,
        color: AppColors.mediumGrey,
        size: 20,
      ),
    );
  }
}
