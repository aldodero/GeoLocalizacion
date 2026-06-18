import 'dart:convert';
import 'package:http/http.dart' as http;

class HistorialService {
  final String baseUrl = "http://10.0.2.2:8084"; 







 Future<void> guardarHistorial(Map<String, dynamic> data) async {
  final url = Uri.parse("$baseUrl/api/historial/guardar");

  try {
    final response = await http.post(
      url,
      headers: {"Content-Type": "application/json"},
      body: jsonEncode(data),
    );

    print("STATUS GUARDAR: ${response.statusCode}");
    print("BODY GUARDAR: ${response.body}");

  } catch (e) {
    print("ERROR HISTORIAL: $e");
  }
}



  Future<List<dynamic>> obtenerHistorial() async {
  final url = Uri.parse("$baseUrl/api/historial/listar");

  try {
    print("CONSULTANDO HISTORIAL...");
    print(url);

    final response = await http
        .get(url)
        .timeout(const Duration(seconds: 5));

    print("STATUS HISTORIAL: ${response.statusCode}");
    print("BODY HISTORIAL: ${response.body}");

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


Future<List<dynamic>> obtenerHistorialPorLocal(
  int idUsuario,
  int idLocal,
) async {

  final url = Uri.parse(
    "$baseUrl/api/historial/listar/$idUsuario/$idLocal",
  );

  try {

    final response = await http.get(url);

    if (response.statusCode == 200) {

      return jsonDecode(response.body);
    }

    return [];

  } catch (e) {

    print(
      "ERROR HISTORIAL LOCAL: $e",
    );

    return [];
  }
}


}