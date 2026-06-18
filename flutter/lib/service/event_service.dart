import 'dart:convert';
import 'package:http/http.dart' as http;

class EventService {

  final String baseUrl =
      "http://10.0.2.2:8089";

  Future<List<dynamic>> obtenerEventos(
    int idUsuario,
    int idLocal,
  ) async {

    final url = Uri.parse(
      "$baseUrl/api/Eventos/listar/$idUsuario/$idLocal",
    );

    final response = await http.get(url);

    print("STATUS EVENTOS: ${response.statusCode}");
    print("BODY EVENTOS: ${response.body}");

    if (response.statusCode == 200) {

      return jsonDecode(response.body);

    } else {

      throw Exception(
        "Error al obtener eventos",
      );
    }
  }

  Future<void> crearEvento({
    required String titulo,
    required String descripcion,
    required DateTime fecha,
    required int idUsuario,
    required int idLocal,
    required int idTipoEvento,
  }) async {

    final url = Uri.parse(
      "$baseUrl/api/Eventos/crear",
    );

    final bodyRequest = {
      "titulo": titulo,
      "descripcion": descripcion,
      "fechaEvento":
          fecha.toIso8601String().split("T")[0],
      "horaEvento":
          fecha.toIso8601String(),
      "idUsuario": idUsuario,
      "idLocal": idLocal,
      "tipoEvento": {
        "idTipoEvento": idTipoEvento
      }
    };

    print("URL EVENTO:");
    print(url);

    print("BODY EVENTO:");
    print(jsonEncode(bodyRequest));

    final response = await http.post(
      url,
      headers: {
        "Content-Type": "application/json",
      },
      body: jsonEncode(
        bodyRequest,
      ),
    );

    print(
      "STATUS CREAR EVENTO: ${response.statusCode}",
    );

    print(
      "BODY CREAR EVENTO: ${response.body}",
    );

    if (response.statusCode != 200 &&
        response.statusCode != 201) {

      throw Exception(
        "Error al crear evento",
      );
    }
  }


  //Eliminar un evento dentro del modulo de calendario
    Future<void> eliminarEvento(
    int idEvento,
  ) async {

    final url = Uri.parse(
      "$baseUrl/api/Eventos/Eliminar/$idEvento",
    );

    final response =
        await http.delete(url);

    if (response.statusCode != 200) {

      throw Exception(
        "Error al eliminar evento",
      );
    }
  }

  //actualizar un evento en el modulo de calendario
  Future<void> actualizarEvento({
  required int idEvento,
  required String titulo,
  required String descripcion,
  required DateTime fecha,
}) async {

  final url = Uri.parse(
    "$baseUrl/api/Eventos/Actualizar/$idEvento",
  );

  final response = await http.put(
    url,
    headers: {
      "Content-Type": "application/json",
    },
    body: jsonEncode({
      "titulo": titulo,
      "descripcion": descripcion,
      "fechaEvento":
          fecha.toIso8601String().split("T")[0],
      "horaEvento":
          fecha.toIso8601String(),
    }),
  );

  if (response.statusCode != 200) {

    throw Exception(
      "Error al actualizar evento",
    );
  }
}


}