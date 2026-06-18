import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../models/calendar_event.dart';
import '../service/calendar_service.dart';
import 'create_event_page.dart';
import '../../../service/event_service.dart';


class CalendarPage extends StatefulWidget {

  final int idUsuario;
  final int idLocal;
  

  const CalendarPage({
    super.key, 
  required this.idUsuario,
   required this.idLocal,
  });

  @override
  State<CalendarPage> createState() => _CalendarPageState();
}

class _CalendarPageState extends State<CalendarPage> {
  final CalendarService _calendarService = CalendarService();
  final EventService eventService =EventService();
  List<CalendarEvent> _allEvents = [];
  DateTime _selectedDate = DateTime.now();
  DateTime _currentMonth = DateTime.now();
  List<CalendarEvent> _selectedDateEvents = [];

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance
    .addPostFrameCallback((_) {

  _loadEventsForSelectedDate();
});

  }

  Future<void> _loadEventsForSelectedDate() async {

  try {

    final data =
        await eventService.obtenerEventos(
      widget.idUsuario,
      widget.idLocal,
    );

    _allEvents =
        data.map<CalendarEvent>((e) {

      EventType tipo =
          EventType.recordatorio;

      final nombreTipo =
          e["tipoEvento"]
                  ["nombreTipoEvento"]
              .toString()
              .toLowerCase();

      if (nombreTipo ==
          "vencimiento") {

        tipo =
            EventType.vencimiento;

      } else if (nombreTipo ==
          "precio") {

        tipo =
            EventType.precio;

      } else if (nombreTipo ==
          "inventario") {

        tipo =
            EventType.inventario;
      }

      return CalendarEvent(
        id: e["idEvento"],
        title: e["titulo"],
        description:
            e["descripcion"],
        date: DateTime.parse(
            e["fechaEvento"]),
        type: tipo,
      );
    }).toList();

    setState(() {

      _selectedDateEvents =
          _allEvents.where((event) {

        return event.date.year ==
                _selectedDate.year &&
            event.date.month ==
                _selectedDate.month &&
            event.date.day ==
                _selectedDate.day;
      }).toList();
    });

  } catch (e) {

    print(
      "ERROR EVENTOS: $e",
    );
  }
}
  

  void _onDateSelected(
      DateTime date
  ) {

    setState(() {

      _selectedDate = date;

      _selectedDateEvents =
          _allEvents.where((event) {

        return event.date.year ==
                date.year &&
            event.date.month ==
                date.month &&
            event.date.day ==
                date.day;
      }).toList();
    });
  }

  void _previousMonth() {
    setState(() {
      _currentMonth = DateTime(_currentMonth.year, _currentMonth.month - 1);
    });
  }

  void _nextMonth() {
    setState(() {
      _currentMonth = DateTime(_currentMonth.year, _currentMonth.month + 1);
    });
  }

//ELIMINAR UN EVENTO
  Future<void> _eliminarEvento(
  CalendarEvent event,
) async {

  final confirmar =
      await showDialog<bool>(
    context: context,
    builder: (context) {
      return AlertDialog(
        title: const Text(
          "Eliminar evento",
        ),
        content: Text(
          "¿Deseas eliminar '${event.title}'?",
        ),
        actions: [

          TextButton(
            onPressed: () {
              Navigator.pop(
                context,
                false,
              );
            },
            child: const Text(
              "Cancelar",
            ),
          ),

          TextButton(
            onPressed: () {
              Navigator.pop(
                context,
                true,
              );
            },
            child: const Text(
              "Eliminar",
            ),
          ),
        ],
      );
    },
  );

  if (confirmar != true) {
    return;
  }

  try {

    await eventService
        .eliminarEvento(
      event.id,
    );

    await _loadEventsForSelectedDate();

    if (!mounted) return;

    ScaffoldMessenger.of(context)
        .showSnackBar(
      const SnackBar(
        content: Text(
          "Evento eliminado",
        ),
      ),
    );

  } catch (e) {

    if (!mounted) return;

    ScaffoldMessenger.of(context)
        .showSnackBar(
      const SnackBar(
        content: Text(
          "Error al eliminar",
        ),
      ),
    );
  }
}

//MOSTRAR OPCIONES DE EVENTO
  void _mostrarOpcionesEvento(
    CalendarEvent event,
  ) {
    showModalBottomSheet(
      context: context,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(
          top: Radius.circular(20),
        ),
      ),
      builder: (BuildContext context) {
        return Container(
          padding: const EdgeInsets.all(20),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // TÍTULO DEL EVENTO
              Text(
                event.title,
                style: Theme.of(context).textTheme.titleLarge?.copyWith(
                  fontWeight: FontWeight.w600,
                ),
              ),
              const SizedBox(height: 8),
              
              // DESCRIPCIÓN COMPLETA
              if (event.description.isNotEmpty) ...[
                Text(
                  event.description,
                  style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                    color: AppColors.mediumGrey,
                  ),
                ),
                const SizedBox(height: 12),
              ],
              
              // TIPO DE EVENTO
              Container(
                padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                decoration: BoxDecoration(
                  color: _getEventTypeColor(event.type).withOpacity(0.1),
                  borderRadius: BorderRadius.circular(8),
                ),
                child: Text(
                  event.type.displayName,
                  style: TextStyle(
                    color: _getEventTypeColor(event.type),
                    fontSize: 12,
                    fontWeight: FontWeight.w600,
                  ),
                ),
              ),
              const SizedBox(height: 16),
              
              // SEPARADOR VISUAL
              Divider(
                color: AppColors.mediumGrey.withOpacity(0.3),
                thickness: 1,
              ),
              const SizedBox(height: 8),
              
              // ACCIONES
              ListTile(
                leading: const Icon(Icons.edit),
                title: const Text("Editar"),
                onTap: () async {
                  Navigator.pop(context);
                  final result = await Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => CreateEventPage(
                        idUsuario: widget.idUsuario,
                        idLocal: widget.idLocal,
                        eventoEditar: event,
                      ),
                    ),
                  );
                  if (result == true) {
                    _loadEventsForSelectedDate();
                  }
                },
              ),
              ListTile(
                leading: const Icon(
                  Icons.delete,
                  color: Colors.red,
                ),
                title: const Text(
                  "Eliminar",
                  style: TextStyle(
                    color: Colors.red,
                  ),
                ),
                onTap: () {
                  Navigator.pop(context);
                  _eliminarEvento(event);
                },
              ),
              const SizedBox(height: 20),
            ],
          ),
        );
      },
    );
  }





  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        backgroundColor: AppColors.white,
        elevation: 0,
        title: const Text(
          'Calendario',
          style: TextStyle(
            color: AppColors.black,
            fontWeight: FontWeight.w600,
          ),
        ),
        centerTitle: true,
      ),
      body: Column(
        children: [
          // CALENDAR HEADER
          Container(
            color: AppColors.white,
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                IconButton(
                  onPressed: _previousMonth,
                  icon: const Icon(Icons.chevron_left, color: AppColors.orange),
                ),
                Text(
                  _getMonthYearString(_currentMonth),
                  style: Theme.of(context).textTheme.titleMedium?.copyWith(
                    fontWeight: FontWeight.w600,
                  ),
                ),
                IconButton(
                  onPressed: _nextMonth,
                  icon: const Icon(Icons.chevron_right, color: AppColors.orange),
                ),
              ],
            ),
          ),

          // CALENDAR GRID
          Container(
            color: AppColors.white,
            child: _buildCalendarGrid(),
          ),

          const SizedBox(height: 16),



          // SELECTED DATE EVENTS
          Expanded(
            child: _buildEventsList(),
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          final result = await Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => CreateEventPage(
                idUsuario:widget.idUsuario,
                idLocal: widget.idLocal,
              ),
            ),
          );
          if (result == true) {
            _loadEventsForSelectedDate();
          }
        },
        backgroundColor: AppColors.orange,
        child: const Icon(Icons.add, color: AppColors.white),
      ),
    );
  }

  Widget _buildCalendarGrid() {
    final daysInMonth = DateTime(_currentMonth.year, _currentMonth.month + 1, 0).day;
    final firstDayOfMonth = DateTime(_currentMonth.year, _currentMonth.month, 1);
    final firstWeekday = firstDayOfMonth.weekday % 7;

    return Column(
      children: [
        // WEEKDAY HEADERS
        Container(
          padding: const EdgeInsets.symmetric(vertical: 8),
          child: Row(
            children: ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb']
                .map((day) => Expanded(
                      child: Center(
                        child: Text(
                          day,
                          style: Theme.of(context).textTheme.bodySmall?.copyWith(
                            color: AppColors.mediumGrey,
                            fontWeight: FontWeight.w500,
                          ),
                        ),
                      ),
                    ))
                .toList(),
          ),
        ),

        // CALENDAR DAYS
        ...List.generate(6, (weekIndex) {
          return Row(
            children: List.generate(7, (dayIndex) {
              final dayNumber = weekIndex * 7 + dayIndex - firstWeekday + 1;
              
              if (dayNumber < 1 || dayNumber > daysInMonth) {
                return const Expanded(child: SizedBox(height: 48));
              }

              final date = DateTime(_currentMonth.year, _currentMonth.month, dayNumber);
              final isSelected = _isSameDay(date, _selectedDate);
              final isToday = _isSameDay(date, DateTime.now());
              final hasEvents =
              _allEvents.any((event) {

            return event.date.year ==
                    date.year &&
                event.date.month ==
                    date.month &&
                event.date.day ==
                    date.day;
              });

              return Expanded(
                child: GestureDetector(
                  onTap: () => _onDateSelected(date),
                  child: Container(
                    height: 48,
                    margin: const EdgeInsets.all(2),
                    decoration: BoxDecoration(
                      color: isSelected ? AppColors.orange : Colors.transparent,
                      borderRadius: BorderRadius.circular(8),
                      border: isToday && !isSelected
                          ? Border.all(color: AppColors.orange, width: 1)
                          : null,
                    ),
                    child: Stack(
                      children: [
                        Center(
                          child: Text(
                            dayNumber.toString(),
                            style: TextStyle(
                              color: isSelected
                                  ? AppColors.white
                                  : isToday
                                      ? AppColors.orange
                                      : AppColors.black,
                              fontWeight: isToday ? FontWeight.w600 : FontWeight.normal,
                            ),
                          ),
                        ),
                        if (hasEvents)
                          Positioned(
                            bottom: 4,
                            right: 0,
                            left: 0,
                            child: Center(
                              child: Container(
                                width: 6,
                                height: 6,
                                decoration: BoxDecoration(
                                  color: isSelected ? AppColors.white : AppColors.orange,
                                  shape: BoxShape.circle,
                                ),
                              ),
                            ),
                          ),
                      ],
                    ),
                  ),
                ),
              );
            }),
          );
        }).where((row) {
          // Solo mostrar filas que tengan al menos un día del mes actual
          return true;
        }).take(6),
      ],
    );
  }

  Widget _buildEventsList() {
    if (_selectedDateEvents.isEmpty) {
      return Container(
        margin: const EdgeInsets.symmetric(horizontal: 16),
        padding: const EdgeInsets.all(24),
        decoration: BoxDecoration(
          color: AppColors.white,
          borderRadius: BorderRadius.circular(16),
          boxShadow: [
            BoxShadow(
              color: AppColors.black.withOpacity(0.04),
              blurRadius: 8,
              offset: const Offset(0, 2),
            ),
          ],
        ),
        child: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Icon(
                Icons.event_note_outlined,
                size: 48,
                color: AppColors.mediumGrey,
              ),
              const SizedBox(height: 12),
              Text(
                'No hay eventos',
                style: Theme.of(context).textTheme.titleMedium?.copyWith(
                  color: AppColors.darkGrey,
                ),
              ),
              const SizedBox(height: 4),
              Text(
                'No tienes eventos programados para este día',
                style: Theme.of(context).textTheme.bodySmall?.copyWith(
                  color: AppColors.mediumGrey,
                ),
                textAlign: TextAlign.center,
              ),
            ],
          ),
        ),
      );
    }

    return ListView.builder(
      padding: const EdgeInsets.symmetric(horizontal: 16),
      itemCount: _selectedDateEvents.length,
      itemBuilder: (context, index) {
        final event = _selectedDateEvents[index];
        return Container(
          margin: const EdgeInsets.only(bottom: 12),
          padding: const EdgeInsets.all(16),
          decoration: BoxDecoration(
            color: AppColors.white,
            borderRadius: BorderRadius.circular(12),
            boxShadow: [
              BoxShadow(
                color: AppColors.black.withOpacity(0.04),
                blurRadius: 8,
                offset: const Offset(0, 2),
              ),
            ],
          ),
          child: Row(
            children: [
              // TIPO DE EVENTO
              Container(
                padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                decoration: BoxDecoration(
                  color: _getEventTypeColor(event.type).withOpacity(0.1),
                  borderRadius: BorderRadius.circular(8),
                ),
                child: Text(
                  event.type.displayName,
                  style: TextStyle(
                    color: _getEventTypeColor(event.type),
                    fontSize: 12,
                    fontWeight: FontWeight.w600,
                  ),
                ),
              ),
              const SizedBox(width: 12),
              
              // TÍTULO DEL EVENTO
              Expanded(
                child: Text(
                  event.title,
                  style: Theme.of(context).textTheme.titleMedium?.copyWith(
                    fontWeight: FontWeight.w600,
                  ),
                ),
              ),
              
              // BOTÓN DE FLECHA
              GestureDetector(
                onTap: () {
                  _mostrarOpcionesEvento(event);
                },
                child: Container(
                  padding: const EdgeInsets.all(8),
                  child: Icon(
                    Icons.chevron_right,
                    color: AppColors.orange,
                    size: 24,
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }


  bool _isSameDay(DateTime date1, DateTime date2) {
    return date1.year == date2.year &&
        date1.month == date2.month &&
        date1.day == date2.day;
  }

  String _getMonthYearString(DateTime date) {
    const months = [
      'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
      'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
    ];
    return '${months[date.month - 1]} ${date.year}';
  }

  Color _getEventTypeColor(EventType type) {
    switch (type) {
      case EventType.vencimiento:
        return Colors.red;
      case EventType.precio:
        return Colors.green;
      case EventType.inventario:
        return Colors.blue;
      case EventType.recordatorio:
        return AppColors.orange;
    }
  }


}