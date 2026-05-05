import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

class MapRealPage extends StatelessWidget {
  final double lat;
  final double lng;
  final String nombre;

  const MapRealPage({
    super.key,
    required this.lat,
    required this.lng,
    required this.nombre,
  });

  @override
  Widget build(BuildContext context) {
    final LatLng posicion = LatLng(lat, lng);

    return Scaffold(
      appBar: AppBar(
        title: Text(nombre),
        backgroundColor: Colors.black,
      ),
      body: GoogleMap(
        initialCameraPosition: CameraPosition(
          target: posicion,
          zoom: 17,
        ),
        markers: {
          Marker(
            markerId: const MarkerId("local"),
            position: posicion,
            infoWindow: InfoWindow(title: nombre),
          ),
        },
      ),
    );
  }
}