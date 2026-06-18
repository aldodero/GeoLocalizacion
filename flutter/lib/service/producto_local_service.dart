import 'dart:convert';
import 'package:http/http.dart' as http;

class ProductoLocalService {

  final String baseUrl =
      "http://10.0.2.2:8088/api/productoLocal";

  Future<Map<String, dynamic>>
      obtenerResumen(int idLocal) async {

    final response = await http.get(

      Uri.parse(
        "$baseUrl/resumen/$idLocal",
      ),
    );

    if (response.statusCode == 200) {

      return json.decode(response.body);

    } else {

      throw Exception(
        "Error resumen productos",
      );
    }
  }


  Future<List<dynamic>> obtenerProductosLocal(
    int idLocal,
) async {

  try {
    final url = "$baseUrl/listar/$idLocal";
    print("Intentando conectar a: $url");
    
    final response = await http.get(
      Uri.parse(url),
      headers: {
        'Content-Type': 'application/json',
      },
    );

    print("Status Code: ${response.statusCode}");
    print("Response Body: ${response.body}");

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      if (data is List) {
        return data;
      } else {
        print("La respuesta no es una lista: $data");
        return [];
      }
    } else {
      print("Error HTTP: ${response.statusCode} - ${response.body}");
      return [];
    }
  } catch (e) {
    print("Error de conexión: $e");
    return [];
  }
}


}