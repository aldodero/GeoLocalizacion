import 'dart:convert';
import 'package:http/http.dart' as http;

class NotificacionService {
  final String baseUrl = "http://10.0.2.2:8086/api/notificaciones";

  Future<List<dynamic>> obtenerNotificaciones(int idUsuario, int idLocal) async {
    final url = "$baseUrl/usuario-local/$idUsuario/$idLocal";
    
    print("ID USUARIO: $idUsuario");
    print("ID LOCAL: $idLocal");
    print("URL NOTIFICACIONES: $url");
    
    final response = await http.get(Uri.parse(url));

    print("STATUS NOTIFICACIONES: ${response.statusCode}");
    print("BODY NOTIFICACIONES: ${response.body}");

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception("Error obteniendo notificaciones");
    }
  }

  Future<int> obtenerConteoNoLeidas(int idUsuario, int idLocal) async {
    final url = "$baseUrl/usuario-local/$idUsuario/$idLocal/conteo";
    
    print("URL CONTEO: $url");
    
    final response = await http.get(Uri.parse(url));

    print("STATUS CONTEO: ${response.statusCode}");
    print("BODY CONTEO: ${response.body}");

    if (response.statusCode == 200) {
      return int.parse(response.body);
    } else {
      throw Exception("Error conteo notificaciones");
    }
  }

  Future<void> marcarLeida(int idNotificacion) async {
    final response = await http.put(
      Uri.parse("$baseUrl/leer/$idNotificacion"),
    );

    if (response.statusCode != 200) {
      throw Exception("Error marcando notificación");
    }
  }
}