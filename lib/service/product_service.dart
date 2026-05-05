import 'dart:convert';
import 'package:http/http.dart' as http;

class ProductService {
  final String baseUrl = "http://10.0.2.2:8080";

  Future<List<dynamic>> buscarProductos(String nombre) async {
    final url = Uri.parse("$baseUrl/api/productos/completo?nombre=$nombre");

    final response = await http.get(url);

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {}
    throw Exception("Error al obtener productos");
  }
}
