import 'dart:convert';
import 'package:http/http.dart' as http;

class UsuarioLocalService {

  final String baseUrl =
      "http://10.0.2.2:8083";

  Future<List<dynamic>> obtenerLocalesUsuario(
      int idUsuario
  ) async {

    final url = Uri.parse(
      "$baseUrl/api/Usuario-Local/Obtener-usuario/$idUsuario",
    );

    try {

      final response = await http.get(url);

      if (response.statusCode == 200) {

        return jsonDecode(response.body);

      } else {

        return [];
      }

    } catch (e) {

      print("ERROR USUARIO LOCAL: $e");

      return [];
    }
  }
}