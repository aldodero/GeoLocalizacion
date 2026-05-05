import 'dart:convert';
import 'package:http/http.dart' as http;

class HistorialService {
  final String baseUrl = "http://10.0.2.2:8084"; // 🔥 puerto historial

  Future<void> guardarHistorial(Map<String, dynamic> data) async {
    final url = Uri.parse("$baseUrl/api/historial/guardar");

    try {
      await http.post(
        url,
        headers: {"Content-Type": "application/json"},
        body: jsonEncode(data),
      );

      print("Historial guardado");
    } catch (e) {
      print("ERROR HISTORIAL: $e");
    }
  }



  Future<List<dynamic>> obtenerHistorial() async {
  final url = Uri.parse("$baseUrl/api/historial/listar");

  try {
    final response = await http.get(url);

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      return [];
    }
  } catch (e) {
    print("ERROR HISTORIAL GET: $e");
    return [];
  }
}


}