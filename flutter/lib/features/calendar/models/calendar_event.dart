class CalendarEvent {
  final int id;
  final String title;
  final String description;
  final DateTime date;
  final EventType type;

  CalendarEvent({
    required this.id,
    required this.title,
    required this.description,
    required this.date,
    required this.type,
  });

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'description': description,
      'date': date.toIso8601String(),
      'type': type.name,
    };
  }

  factory CalendarEvent.fromJson(Map<String, dynamic> json) {
    return CalendarEvent(
      id: json['id'],
      title: json['title'],
      description: json['description'],
      date: DateTime.parse(json['date']),
      type: EventType.values.firstWhere(
        (e) => e.name == json['type'],
        orElse: () => EventType.recordatorio,
      ),
    );
  }
}

enum EventType {
  vencimiento,
  precio,
  inventario,
  recordatorio,
}

extension EventTypeExtension on EventType {
  String get displayName {
    switch (this) {
      case EventType.vencimiento:
        return 'Vencimiento';
      case EventType.precio:
        return 'Precio';
      case EventType.inventario:
        return 'Inventario';
      case EventType.recordatorio:
        return 'Recordatorio';
    }
  }
}