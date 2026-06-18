import 'dart:convert';
import 'package:http/http.dart' as http;

class ReporteService {
  final String baseUrl = "http://10.0.2.2:8087"; 

  // Crear nuevo reporte de incidencia
  Future<bool> crearReporte(Map<String, dynamic> data) async {
    final url = Uri.parse("$baseUrl/api/reportes/crear");

    try {
      final response = await http.post(
        url,
        headers: {"Content-Type": "application/json"},
        body: jsonEncode(data),
      );

      print("STATUS CREAR REPORTE: ${response.statusCode}");
      print("BODY CREAR REPORTE: ${response.body}");

      return response.statusCode == 200 || response.statusCode == 201;
    } catch (e) {
      print("ERROR CREAR REPORTE: $e");
      return false;
    }
  }

  // Obtener reportes del usuario
  Future<List<dynamic>> obtenerReportesUsuario(int idUsuario) async {
    final url = Uri.parse("$baseUrl/api/reportes/usuario/$idUsuario");

    try {
      final response = await http
          .get(url)
          .timeout(const Duration(seconds: 5));

      print("STATUS REPORTES USUARIO: ${response.statusCode}");
      print("BODY REPORTES USUARIO: ${response.body}");

      if (response.statusCode == 200) {
        return jsonDecode(response.body);
      } else {
        return [];
      }
    } catch (e) {
      print("ERROR OBTENER REPORTES: $e");
      return [];
    }
  }


  // Obtener detalle de un reporte específico
  Future<Map<String, dynamic>?> obtenerDetalleReporte(int idReporte) async {
    final url = Uri.parse("$baseUrl/api/reportes/$idReporte");

    try {
      final response = await http.get(url);

      if (response.statusCode == 200) {
        return jsonDecode(response.body);
      } else {
        return null;
      }
    } catch (e) {
      print("ERROR DETALLE REPORTE: $e");
      return null;
    }
  }


  // Actualizar estado del reporte (admin)
  Future<bool> actualizarEstadoReporte(int idReporte, String nuevoEstado) async {
    final url = Uri.parse("$baseUrl/api/reportes/estado/$idReporte");

    try {
      final response = await http.put(
        url,
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({"estado": nuevoEstado}),
      );

      return response.statusCode == 200;
    } catch (e) {
      print("ERROR ACTUALIZAR ESTADO: $e");
      return false;
    }
  }



// Obtener reportes por usuario y local
Future<List<dynamic>> obtenerReportesUsuarioLocal(
  int idUsuario,
  int idLocal,
) async {

  final url = Uri.parse(
    "$baseUrl/api/reportes/usuario-local/$idUsuario/$idLocal",
  );

  try {
    final response = await http
        .get(url)
        .timeout(const Duration(seconds: 5));

    print("STATUS REPORTES LOCAL: ${response.statusCode}");
    print("BODY REPORTES LOCAL: ${response.body}");

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      return [];
    }
  } catch (e) {
    print("ERROR REPORTES LOCAL: $e");
    return [];
  }
}


}
