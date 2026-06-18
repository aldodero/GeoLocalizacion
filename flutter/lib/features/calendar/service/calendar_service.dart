import '../models/calendar_event.dart';

class CalendarService {
  static final CalendarService _instance = CalendarService._internal();
  factory CalendarService() => _instance;
  CalendarService._internal();

  final List<CalendarEvent> _events = [
    CalendarEvent(
      id: 1,
      title: 'Coca Cola vence',
      description: 'Revisar productos próximos a vencer',
      date: DateTime.now().add(const Duration(days: 2)),
      type: EventType.vencimiento,
    ),
    CalendarEvent(
      id: 2,
      title: 'Revisar precios',
      description: 'Actualizar precios de productos lácteos',
      date: DateTime.now().add(const Duration(days: 5)),
      type: EventType.precio,
    ),
    CalendarEvent(
      id: 3,
      title: 'Inventario semanal',
      description: 'Conteo de inventario general',
      date: DateTime.now().add(const Duration(days: 7)),
      type: EventType.inventario,
    ),
    CalendarEvent(
      id: 4,
      title: 'Reunión equipo',
      description: 'Reunión mensual del equipo',
      date: DateTime.now().add(const Duration(days: 10)),
      type: EventType.recordatorio,
    ),
  ];

  int _nextId = 5;

  List<CalendarEvent> getAllEvents() {
    return List.from(_events);
  }

  List<CalendarEvent> getEventsForDate(DateTime date) {
    return _events.where((event) {
      return event.date.year == date.year &&
          event.date.month == date.month &&
          event.date.day == date.day;
    }).toList();
  }

  List<CalendarEvent> getEventsForMonth(int year, int month) {
    return _events.where((event) {
      return event.date.year == year && event.date.month == month;
    }).toList();
  }

  void addEvent(CalendarEvent event) {
    final newEvent = CalendarEvent(
      id: _nextId++,
      title: event.title,
      description: event.description,
      date: event.date,
      type: event.type,
    );
    _events.add(newEvent);
  }

  void removeEvent(int id) {
    _events.removeWhere((event) => event.id == id);
  }

  bool hasEventsOnDate(DateTime date) {
    return _events.any((event) {
      return event.date.year == date.year &&
          event.date.month == date.month &&
          event.date.day == date.day;
    });
  }
}