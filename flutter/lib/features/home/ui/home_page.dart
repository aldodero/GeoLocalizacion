import 'package:flutter/material.dart';
import 'dart:async';
import '../../../core/theme/app_theme.dart';

import '../../notification/ui/notification_page.dart';
import '../../search/ui/search_page.dart';
import '../../favorites/ui/favorites_page.dart';
import '../../history/ui/history_page.dart';
import '../../map/ui/map_overview_page.dart';
import '../../map/ui/map_real_page.dart';
import '../../scanner/ui/scanner_page.dart';
import '../../../service/usuario_local_service.dart';
import '../../../service/notificacion_service.dart';
import '../../profile/ui/profile_page.dart';
import '../../locations/ui/my_locations_page.dart';
import '../../activity/ui/activity_page.dart';
import '../../settings/ui/settings_page.dart';
import '../../reports/ui/reports_page.dart';
import '../../notification/ui/notification_page.dart';
import '../../../service/producto_local_service.dart';
import '../../productos/ui/productos_local_page.dart';
import '../../calendar/ui/calendar_page.dart';



class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {

  int currentIndex = 0;

  final UsuarioLocalService usuarioLocalService =
      UsuarioLocalService();

  final ProductoLocalService productolocalservice=
      ProductoLocalService();

  final NotificacionService notificacionService =
      NotificacionService();



//Variables
  List<dynamic> locales = [];

  Map<String, dynamic>? localActivo;

  Map<String, dynamic>? resumenProductos;

  int cantidadNoLeidas = 0;

  Timer? notificationTimer;



  @override
  void initState() {
    super.initState();

    cargarLocal();
    cargarNotificaciones();

    notificationTimer = Timer.periodic(
      const Duration(seconds: 5),
      (_) {
        cargarNotificaciones();
      },
    );

  }


  Future<void> cargarResumenProductos() async {

  if (localActivo == null) return;

  try {

    final data =
        await productolocalservice
            .obtenerResumen(
                localActivo!["idLocal"]);
    print(data);

    setState(() {

      resumenProductos = data;
    });

  } catch (e) {

    print(
      "ERROR RESUMEN PRODUCTOS: $e",
    );
  }
}


//navegar a productos del local 
Future<void> mostrarProductosLocal() async {

  if (localActivo == null) return;

  Navigator.push(
    context,
    MaterialPageRoute(
      builder: (context) => ProductosLocalPage(
        idLocal: localActivo!["idLocal"],
        nombreLocal: localActivo!["nombreLocal"] ?? "Local sin nombre",
      ),
    ),
  );
}


  @override
  void dispose() {
    notificationTimer?.cancel();
    super.dispose();
  }

  Future<void> cargarNotificaciones() async {

    try {

      final total =
          await notificacionService
              .obtenerConteoNoLeidas(1, localActivo?["idLocal"] ?? 0);

      setState(() {

        cantidadNoLeidas = total;
      });

    } catch (e) {

      print(
        "ERROR BADGE NOTIFICACIONES: $e",
      );
    }
  }


//metodo para cargar locales
 Future<void> cargarLocal() async {

  print("ENTRO A CARGAR LOCAL");
  final data =
      await usuarioLocalService
          .obtenerLocalesUsuario(1);

  print("DATA LOCALES:");
  print(data);

  setState(() {

    locales = data;

    if (data.isEmpty) {

      print("SIN LOCALES");
      localActivo = null;

    } else {

      final existe = data.any(
        (local) =>
            local["idLocal"] ==
            localActivo?["idLocal"],
      );

      if (!existe) {

        print("PRIMER LOCAL:");
        print(data[0]);

        localActivo = data[0];
      }
    }
  });

  await cargarResumenProductos();
}





  Future<void> mostrarLocales() async {

    await cargarLocal();

    showModalBottomSheet(

      context: context,

      backgroundColor: AppColors.white,

      shape: const RoundedRectangleBorder(

        borderRadius: BorderRadius.vertical(
          top: Radius.circular(24),
        ),
      ),

      builder: (context) {

        return Padding(

          padding: const EdgeInsets.all(20),

          child: Column(

            mainAxisSize: MainAxisSize.min,

            children: [

              const SizedBox(height: 8),

              Text(

                "Selecciona un local",

                style: Theme.of(context)
                    .textTheme
                    .titleLarge,
              ),

              const SizedBox(height: 20),

              ...locales.map((local) {

                return ListTile(

                  leading: const Icon(

                    Icons.store,

                    color: AppColors.orange,
                  ),

                  title: Text(
                    local["nombreLocal"] ?? "",
                  ),

                  subtitle: Text(
                    local["direccionLocal"] ?? "",
                  ),

                  onTap: () async {

                    setState(() {

                      localActivo = local;
                      resumenProductos = null;
                    });
                    await cargarResumenProductos();
                    await cargarNotificaciones();

                    Navigator.pop(context);
                  },
                );
              }),
            ],
          ),
        );
      },
    );
  }

  void mostrarMenuPerfil() {

    showModalBottomSheet(

      context: context,

      backgroundColor: AppColors.white,

      shape: const RoundedRectangleBorder(

        borderRadius: BorderRadius.vertical(
          top: Radius.circular(24),
        ),
      ),

      builder: (context) {

        return SafeArea(

          child: Padding(

            padding: const EdgeInsets.all(20),

            child: Column(

              mainAxisSize: MainAxisSize.min,

              children: [

                Container(

                  width: 40,

                  height: 4,

                  decoration: BoxDecoration(

                    color: AppColors.mediumGrey,

                    borderRadius:
                        BorderRadius.circular(10),
                  ),
                ),

                const SizedBox(height: 20),

                ListTile(

                  leading: const Icon(
                    Icons.person,
                  ),

                  title: const Text(
                    "Mi perfil",
                  ),

                  onTap: () {

                    Navigator.pop(context);

                    Navigator.push(

                      context,

                      MaterialPageRoute(

                        builder: (context) =>
                            const ProfilePage(),
                      ),
                    );
                  },
                ),

                ListTile(

                  leading: const Icon(
                    Icons.store,
                  ),

                  title: const Text(
                    "Mis locales",
                  ),

                  //boton para seleccionar el local en el modulo
                  onTap: () async {

                  Navigator.pop(context);

                  final resultado = await Navigator.push(

                    context,

                    MaterialPageRoute(

                      builder: (context) =>
                         MyLocationsPage(
                          localActivoId:
                                localActivo?["idLocal"] ?? 0,
                         ),
                    ),
                  );

                  if (resultado != null) {

                    final local = locales.firstWhere(

                      (l) => l["idLocal"] == resultado,
                    );

                    setState(() {

                      localActivo = local;
                    });

                    await cargarResumenProductos();
                    await cargarNotificaciones();
                  }
                },
              ),

                ListTile(

                  leading: const Icon(
                    Icons.bar_chart,
                  ),

                  title: const Text(
                    "Mi actividad",
                  ),

                  onTap: () {

                    Navigator.pop(context);

                    Navigator.push(

                      context,

                      MaterialPageRoute(

                        builder: (context) =>
                            const ActivityPage(),
                      ),
                    );
                  },
                ),

                ListTile(

                  leading: const Icon(
                    Icons.assessment,
                  ),

                  title: const Text(
                    "Reportes",
                  ),

                  onTap: () {

                    Navigator.pop(context);

                    Navigator.push(

                      context,

                      MaterialPageRoute(

                        builder: (context) =>
                            ReportsPage(
                              idLocal: localActivo?["idLocal"] ?? 0,
                            ),
                      ),
                    );
                  },
                ),

                ListTile(

                  leading: const Icon(
                    Icons.settings,
                  ),

                  title: const Text(
                    "Configuración",
                  ),

                  onTap: () {

                    Navigator.pop(context);

                    Navigator.push(

                      context,

                      MaterialPageRoute(

                        builder: (context) =>
                            const SettingsPage(),
                      ),
                    );
                  },
                ),

                const Divider(),

                ListTile(

                  leading: const Icon(

                    Icons.logout,

                    color: Colors.red,
                  ),

                  title: const Text(

                    "Cerrar sesión",

                    style: TextStyle(
                      color: Colors.red,
                    ),
                  ),

                  onTap: () {},
                ),

                const SizedBox(height: 24),
              ],
            ),
          ),
        );
      },
    );
  }


  @override
  Widget build(BuildContext context) {
    final pages = [

  _homeContent(context),

  localActivo == null
      ? const Center(
          child: CircularProgressIndicator(),
        )
      : CalendarPage(
          idUsuario: 1,
          idLocal: localActivo!["idLocal"] ?? 0,
        ),

  FavoritesPage(
    idLocal: localActivo?["idLocal"] ?? 0,
  ),

  HistoryPage(
    idLocal: localActivo?["idLocal"] ?? 0,
  ),
];

    return Scaffold(

      backgroundColor: AppColors.lightGrey,

      body: pages[currentIndex],

      bottomNavigationBar: BottomNavigationBar(

        type: BottomNavigationBarType.fixed,

        currentIndex: currentIndex,

        onTap: (index) {

          setState(() {

            currentIndex = index;
          });
        },

        items: const [

          BottomNavigationBarItem(

            icon: Icon(Icons.home_outlined),

            activeIcon: Icon(Icons.home),

            label: 'Inicio',
          ),

          BottomNavigationBarItem(

            icon: Icon(Icons.calendar_today_outlined),

            activeIcon: Icon(Icons.calendar_today),

            label: 'Calendario',
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

        padding: const EdgeInsets.symmetric(
          horizontal: 16,
          vertical: 16,
        ),

        child: Column(

          crossAxisAlignment:
              CrossAxisAlignment.start,

          children: [

            // HEADER
            Row(

              mainAxisAlignment:
                  MainAxisAlignment.spaceBetween,

              children: [

                Row(

                  children: [

                    GestureDetector(

                      onTap: mostrarMenuPerfil,

                      child: Container(

                        width: 40,

                        height: 40,

                        decoration: BoxDecoration(

                          color: AppColors.black,

                          borderRadius:
                              BorderRadius.circular(12),
                        ),

                        child: const Icon(

                          Icons.person_outline,

                          color: AppColors.white,

                          size: 22,
                        ),
                      ),
                    ),

                    const SizedBox(width: 10),

                    Column(

                      crossAxisAlignment:
                          CrossAxisAlignment.start,

                      children: [

                        Text(

                          'Hola, Alejandro',

                          style: Theme.of(context)
                              .textTheme
                              .titleMedium,
                        ),

                        Text(

                          'Bienvenido de vuelta',

                          style: Theme.of(context)
                              .textTheme
                              .bodySmall,
                        ),
                      ],
                    ),
                  ],
                ),

                GestureDetector(

                  onTap: () async {

                    await Navigator.push(

                      context,

                      MaterialPageRoute(

                        builder: (context) =>
                            NotificationPage(
                              idLocal: localActivo?["idLocal"] ?? 0,
                            ),
                      ),
                    );

                    cargarNotificaciones();
                  },

                  child: Stack(

                    clipBehavior: Clip.none,

                    children: [

                      Container(

                        width: 40,

                        height: 40,

                        decoration: BoxDecoration(

                          color: AppColors.white,

                          borderRadius:
                              BorderRadius.circular(12),

                          boxShadow: [

                            BoxShadow(

                              color: AppColors.black
                                  .withOpacity(0.06),

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

                      if (cantidadNoLeidas > 0)

                        Positioned(

                          right: -2,

                          top: -2,

                          child: Container(

                            padding:
                                const EdgeInsets.all(4),

                            decoration:
                                const BoxDecoration(

                              color: Colors.red,

                              shape: BoxShape.circle,
                            ),

                            constraints:
                                const BoxConstraints(

                              minWidth: 18,

                              minHeight: 18,
                            ),

                            child: Text(

                              cantidadNoLeidas > 9
                                  ? '9+'
                                  : cantidadNoLeidas
                                      .toString(),

                              style: const TextStyle(

                                color: Colors.white,

                                fontSize: 10,

                                fontWeight:
                                    FontWeight.bold,
                              ),

                              textAlign: TextAlign.center,
                            ),
                          ),
                        ),
                    ],
                  ),
                ),
              ],
            ),

            const SizedBox(height: 28),

            // BANNER LOCAL
            GestureDetector(

              onTap: mostrarLocales,

              child: Container(

                width: double.infinity,

                padding: const EdgeInsets.symmetric(

                  horizontal: 16,

                  vertical: 14,
                ),

                decoration: BoxDecoration(

                  color: const Color(0xFF455A64),

                  borderRadius:
                      BorderRadius.circular(16),
                  
                  boxShadow: const [
                    BoxShadow(
                      color: Colors.black12,
                      blurRadius: 8,
                      offset: Offset(0, 2),
                    ),
                  ],
                ),

                child: Row(

                  children: [

                    Container(

                      width: 36,

                      height: 36,

                      decoration: BoxDecoration(

                        color: const Color(0xFF663D00),

                        borderRadius:
                            BorderRadius.circular(10),
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

                        crossAxisAlignment:
                            CrossAxisAlignment.start,

                        children: [

                          Text(

                            'Local activo',

                            style: Theme.of(context)
                                .textTheme
                                .bodySmall
                                ?.copyWith(

                                  color:
                                      AppColors.mediumGrey,
                                ),
                          ),

                          const SizedBox(height: 2),

                          Row(

                            children: [

                              Expanded(

                                child: Text(

                                  localActivo?[
                                          "nombreLocal"] ??
                                      "sin local asignado",

                                  style: Theme.of(context)
                                      .textTheme
                                      .titleMedium
                                      ?.copyWith(

                                        color:
                                            AppColors.white,

                                        fontSize: 14,
                                      ),
                                ),
                              ),

                              const Icon(

                                Icons.keyboard_arrow_down,

                                color: AppColors.orange,

                                size: 20,
                              ),

                              TextButton(

                                onPressed: () {

                                  Navigator.push(

                                    context,

                                    MaterialPageRoute(

                                      builder: (context) =>
                                          MapRealPage(

                                        lat:
                                            localActivo?[
                                                    "latitud"] ??
                                                0,

                                        lng:
                                            localActivo?[
                                                    "longitud"] ??
                                                0,

                                        nombre:
                                            localActivo?[
                                                    "nombreLocal"] ??
                                                "",
                                      ),
                                    ),
                                  );
                                },

                                child: const Text(

                                  "Ver mapa",

                                  style: TextStyle(
                                    color: Colors.orange,
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
              ),
            ),

            const SizedBox(height: 12),

            // PRODUCTOS DISPONIBLES CARD
            _ProductosDisponiblesCard(
              
              cantidad: 
              resumenProductos == null ? 0:
                  (resumenProductos?["cantidad"]?.toInt() ?? 0),

              onTap: mostrarProductosLocal,
            ),

            const SizedBox(height: 48),

            Text(

              '¿Qué quieres hacer?',

              style: Theme.of(context)
                  .textTheme
                  .titleLarge,
            ),

            const SizedBox(height: 24),

            _ActionCard(

              icon: Icons.qr_code_scanner,

              title: 'Escanear producto',

              subtitle:
                  'Apunta la cámara al código de barras',

              color: AppColors.black,

              onTap: () {

                Navigator.push(

                  context,

                  MaterialPageRoute(

                    builder: (context) =>
                        ScannerPage(
                          idLocal: localActivo?["idLocal"] ?? 0,
                        ),
                  ),
                );
              },
            ),

            const SizedBox(height: 18),

            _ActionCard(

              icon: Icons.search,

              title: 'Buscar producto',

              subtitle:
                  'Encuentra productos por nombre',

              color: AppColors.orange,

              onTap: () {

                Navigator.push(

                  context,

                  MaterialPageRoute(

                    builder: (context) =>
                          SearchPage(
                          idLocal: localActivo?["idLocal"] ?? 0),
                  ),
                );
              },
            ),

            const SizedBox(height: 18),

            _ActionCard(

              icon: Icons.map_outlined,

              title: 'Ver mapa del local',

              subtitle:
                  'Explora la distribución del supermercado',

              color: AppColors.darkGrey,

              onTap: () {

                Navigator.push(

                  context,

                  MaterialPageRoute(

                    builder: (context) =>
                        const MapOverviewPage(),
                  ),
                );
              },
            ),

            const SizedBox(height: 16),
          ],
        ),
      ),
    );
  }
}

class _ProductosDisponiblesCard extends StatelessWidget {

  final int cantidad;

  final VoidCallback onTap;

  const _ProductosDisponiblesCard({

    required this.cantidad,

    required this.onTap,
  });



  @override
  Widget build(BuildContext context) {

    return GestureDetector(

      onTap: onTap,

      child: Container(

        width: double.infinity,

        padding: const EdgeInsets.symmetric(

          horizontal: 16,

          vertical: 14,
        ),


        decoration: BoxDecoration(

          color: const Color(0xFF455A64),

          borderRadius:
              BorderRadius.circular(12),
          
          boxShadow: const [
            BoxShadow(
              color: Colors.black12,
              blurRadius: 8,
              offset: Offset(0, 2),
            ),
          ],
        ),



        child: Row(

          children: [

            Expanded(

              child: Text(

                'productos disponibles en este local: $cantidad',

                style: const TextStyle(

                  color: Colors.white,

                  fontSize: 13,

                  fontWeight: FontWeight.w500,
                ),
              ),
            ),

            const SizedBox(width: 12),

            const Icon(

              Icons.arrow_forward,

              color: Colors.white,

              size: 18,
            ),
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

        padding: const EdgeInsets.all(22),

        decoration: BoxDecoration(

          color: AppColors.white,

          borderRadius:
              BorderRadius.circular(16),

          boxShadow: [

            BoxShadow(

              color: AppColors.black
                  .withOpacity(0.06),

              blurRadius: 12,

              offset: const Offset(0, 3),
            ),
          ],
        ),

        child: Row(

          children: [

            Container(

              width: 56,

              height: 56,

              decoration: BoxDecoration(

                color: color,

                borderRadius:
                    BorderRadius.circular(16),
              ),

              child: Icon(

                icon,

                color: AppColors.white,

                size: 28,
              ),
            ),

            const SizedBox(width: 20),

            Expanded(

              child: Column(

                crossAxisAlignment:
                    CrossAxisAlignment.start,

                children: [

                  Text(

                    title,

                    style: Theme.of(context)
                        .textTheme
                        .titleMedium
                        ?.copyWith(

                          fontSize: 16,

                          fontWeight: FontWeight.w600,
                        ),
                  ),

                  const SizedBox(height: 6),

                  Text(

                    subtitle,

                    style: Theme.of(context)
                        .textTheme
                        .bodySmall
                        ?.copyWith(

                          fontSize: 13,

                          height: 1.3,
                        ),
                  ),
                ],
              ),
            ),

            const Icon(

              Icons.arrow_forward_ios,

              size: 16,

              color: AppColors.mediumGrey,
            ),
          ],
        ),
      ),
    );
  }
}