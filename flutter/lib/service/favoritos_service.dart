import 'dart:convert';
import 'package:http/http.dart' as http;

class FavoritosService {
  final String baseUrl = "http://10.0.2.2:8085"; // 🔥 puerto favoritos

  Future<void> guardarFavorito(Map<String, dynamic> data) async {
    final url = Uri.parse("$baseUrl/api/favoritos/guardar");

    try {
      final response = await http.post(
        url,
        headers: {"Content-Type": "application/json"},
        body: jsonEncode(data),
      );

      print("STATUS GUARDAR FAVORITO: ${response.statusCode}");
      print("BODY GUARDAR FAVORITO: ${response.body}");
    } catch (e) {
      print("ERROR FAVORITO: $e");
      rethrow;
    }
  }



  Future<List<dynamic>> obtenerFavoritos() async {
    final url = Uri.parse("$baseUrl/api/favoritos/listar");

    try {
      print("CONSULTANDO FAVORITOS...");
      print(url);

      final response = await http
          .get(url)
          .timeout(const Duration(seconds: 5));

      print("STATUS FAVORITOS: ${response.statusCode}");
      print("BODY FAVORITOS: ${response.body}");

      if (response.statusCode == 200) {
        return jsonDecode(response.body);
      } else {
        return [];
      }
    } catch (e) {
      print("ERROR FAVORITOS GET: $e");
      return [];
    }
  }




  Future<bool> esFavorito(String codigoProducto) async {
    final favoritos = await obtenerFavoritos();
    return favoritos.any((fav) => fav["codigoProducto"] == codigoProducto);
  }

  Future<void> eliminarFavorito(int idUsuario, int idProducto) async {
    final url = Uri.parse("$baseUrl/api/favoritos/eliminar?idUsuario=$idUsuario&idProducto=$idProducto");

    try {
      final response = await http.delete(url);

      print("STATUS ELIMINAR FAVORITO: ${response.statusCode}");
      print("BODY ELIMINAR FAVORITO: ${response.body}");

      if (response.statusCode != 200) {
        throw Exception("Error al eliminar favorito");
      }
    } catch (e) {
      print("ERROR ELIMINAR FAVORITO: $e");
      rethrow;
    }
  }

  //filtra favoritos por local y user
  Future<List<dynamic>> obtenerFavoritosPorLocal(
  int idUsuario,
  int idLocal,
) async {

  final url = Uri.parse(
    "$baseUrl/api/favoritos/listar/$idUsuario/$idLocal",
  );

  try {

    final response = await http.get(url);

    print(
      "STATUS FAVORITOS LOCAL: ${response.statusCode}",
    );

    print(
      "BODY FAVORITOS LOCAL: ${response.body}",
    );

    if (response.statusCode == 200) {

      return jsonDecode(response.body);
    }

    return [];

  } catch (e) {

    print(
      "ERROR FAVORITOS LOCAL: $e",
    );

    return [];
  }
}



}
