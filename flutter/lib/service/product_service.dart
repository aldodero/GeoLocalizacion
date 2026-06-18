import 'dart:convert';
import 'package:http/http.dart' as http;

class ProductService {

  final String baseUrl =
      "http://10.0.2.2:8088";



  Future<List<dynamic>> buscarProductos(
      int idLocal,
      String nombre,
  ) async {

    final url = Uri.parse(
      "$baseUrl/api/productoLocal/buscar/$idLocal/$nombre",
    );

    final response = await http.get(url);

    if (response.statusCode == 200) {

      return jsonDecode(response.body);

    } else {

      throw Exception(
        "Error al obtener productos",
      );
    }
  }




//buscar por codigo
  Future<Map<String, dynamic>> buscarPorCodigo(
  String codigo,
  int idLocal,
) async {

  final url = Uri.parse(
    "$baseUrl/api/ubicaciones/escanear?codigo=$codigo&idLocal=$idLocal",
  );

  final response = await http.get(url);

  if (response.statusCode == 200) {

    return jsonDecode(response.body);

  } else {

    throw Exception(
      "Producto no encontrado",
    );
  }
}



}