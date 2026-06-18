import 'package:flutter/material.dart';
import '../../../core/theme/app_theme.dart';
import '../models/calendar_event.dart';
import '../service/calendar_service.dart';
import '../../../service/event_service.dart';

class CreateEventPage extends StatefulWidget {
  final int idUsuario;
  final int idLocal;
  final CalendarEvent? eventoEditar;

  const CreateEventPage({
    super.key,
    required this.idUsuario,
    required this.idLocal,
    this.eventoEditar,
  });

  @override
  State<CreateEventPage> createState() => _CreateEventPageState();
}

class _CreateEventPageState extends State<CreateEventPage> {
  final _formKey = GlobalKey<FormState>();
  final _titleController = TextEditingController();
  final _descriptionController = TextEditingController();
  final CalendarService _calendarService = CalendarService();
  final EventService eventService = EventService();
  DateTime _selectedDate = DateTime.now();
  EventType _selectedType = EventType.recordatorio;
  bool _dateHasError = false;

  @override
  void initState() {
    super.initState();
    // Precargar datos si estamos editando un evento
    if (widget.eventoEditar != null) {
      _titleController.text = widget.eventoEditar!.title;
      _descriptionController.text = widget.eventoEditar!.description;
      _selectedDate = widget.eventoEditar!.date;
      _selectedType = widget.eventoEditar!.type;
    }
  }

  @override
  void dispose() {
    _titleController.dispose();
    _descriptionController.dispose();
    super.dispose();
  }

  Future<void> _selectDate() async {
    await showDialog(
      context: context,
      builder: (BuildContext context) {
        return _CustomDatePicker(
          initialDate: _selectedDate,
          onDateSelected: (DateTime date) {
            setState(() {
              _selectedDate = date;
              _dateHasError = false; // Clear error when date is selected
            });
          },
        );
      },
    );
  }


  Future<void> _saveEvent() async{
    // Reset date error state
    setState(() {
      _dateHasError = false;
    });

    // Validar formulario
    if (!_formKey.currentState!.validate()) {
      return;
    }

    // Validar que la fecha no sea en el pasado
    final today = DateTime.now();
    final selectedDateOnly = DateTime(_selectedDate.year, _selectedDate.month, _selectedDate.day);
    final todayOnly = DateTime(today.year, today.month, today.day);
    
    if (selectedDateOnly.isBefore(todayOnly)) {
      setState(() {
        _dateHasError = true;
      });
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('La fecha no puede ser anterior a hoy'),
          backgroundColor: Colors.red,
          duration: Duration(seconds: 2),
        ),
      );
      return;
    }

    // Validar que todos los campos estén completos
    if (_titleController.text.trim().isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('El título es obligatorio'),
          backgroundColor: Colors.red,
          duration: Duration(seconds: 2),
        ),
      );
      return;
    }

    if (_descriptionController.text.trim().isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('La descripción es obligatoria'),
          backgroundColor: Colors.red,
          duration: Duration(seconds: 2),
        ),
      );
      return;
    }

    try {

      if (widget.eventoEditar == null) {
        // MODO CREAR
        int idTipoEvento = 4;

        switch (_selectedType) {
          case EventType.vencimiento:
            idTipoEvento = 1;
            break;
          case EventType.precio:
            idTipoEvento = 2;
            break;
          case EventType.inventario:
            idTipoEvento = 3;
            break;
          case EventType.recordatorio:
            idTipoEvento = 4;
            break;
        }

        await eventService.crearEvento(
          titulo: _titleController.text.trim(),
          descripcion: _descriptionController.text.trim(),
          fecha: _selectedDate,
          idUsuario: widget.idUsuario,
          idLocal: widget.idLocal,
          idTipoEvento: idTipoEvento,
        );

      } else {
        // MODO EDITAR
        await eventService.actualizarEvento(
          idEvento: widget.eventoEditar!.id,
          titulo: _titleController.text.trim(),
          descripcion: _descriptionController.text.trim(),
          fecha: _selectedDate,
        );
      }

      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            widget.eventoEditar == null 
              ? 'Evento guardado exitosamente'
              : 'Evento actualizado exitosamente',
          ),
          backgroundColor: AppColors.orange,
          duration: const Duration(seconds: 2),
        ),
      );

      Navigator.pop(context, true);

    } catch (e) {

      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            'Error al ${widget.eventoEditar == null ? "guardar" : "actualizar"} evento: $e',
          ),
          backgroundColor: Colors.red,
        ),
      );
    }
  }



  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.lightGrey,
      appBar: AppBar(
        backgroundColor: AppColors.white,
        elevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: AppColors.black),
          onPressed: () => Navigator.pop(context),
        ),
        title: Text(
          widget.eventoEditar != null ? 'Editar Evento' : 'Nuevo Evento',
          style: const TextStyle(
            color: AppColors.black,
            fontWeight: FontWeight.w600,
          ),
        ),
        centerTitle: true,
      ),
      body: Form(
        key: _formKey,
        child: ListView(
          padding: const EdgeInsets.all(16),
          children: [
            // TÍTULO
            Container(
              padding: const EdgeInsets.all(20),
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
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Título',
                    style: Theme.of(context).textTheme.titleMedium?.copyWith(
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  const SizedBox(height: 12),
                  TextFormField(
                    controller: _titleController,
                    decoration: const InputDecoration(
                      hintText: 'Ingresa el título del evento',
                      prefixIcon: Icon(Icons.title, color: AppColors.orange),
                    ),
                    validator: (value) {
                      if (value == null || value.trim().isEmpty) {
                        return 'El título es obligatorio';
                      }
                      if (value.trim().length < 3) {
                        return 'El título debe tener al menos 3 caracteres';
                      }
                      return null;
                    },
                  ),
                ],
              ),
            ),

            const SizedBox(height: 16),

            // DESCRIPCIÓN
            Container(
              padding: const EdgeInsets.all(20),
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
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Descripción',
                    style: Theme.of(context).textTheme.titleMedium?.copyWith(
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  const SizedBox(height: 12),
                  TextFormField(
                    controller: _descriptionController,
                    maxLines: 3,
                    decoration: const InputDecoration(
                      hintText: 'Describe los detalles del evento',
                      prefixIcon: Icon(Icons.description, color: AppColors.orange),
                    ),
                    validator: (value) {
                      if (value == null || value.trim().isEmpty) {
                        return 'La descripción es obligatoria';
                      }
                      if (value.trim().length < 10) {
                        return 'La descripción debe tener al menos 10 caracteres';
                      }
                      return null;
                    },
                  ),
                ],
              ),
            ),

            const SizedBox(height: 16),

            // FECHA
            Container(
              padding: const EdgeInsets.all(20),
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
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Fecha',
                    style: Theme.of(context).textTheme.titleMedium?.copyWith(
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  const SizedBox(height: 12),
                  GestureDetector(
                    onTap: _selectDate,
                    child: Container(
                      padding: const EdgeInsets.symmetric(
                        horizontal: 12,
                        vertical: 16,
                      ),
                      decoration: BoxDecoration(
                        border: Border.all(
                          color: _dateHasError ? Colors.red : AppColors.mediumGrey,
                        ),
                        borderRadius: BorderRadius.circular(8),
                      ),
                      child: Row(
                        children: [
                          const Icon(Icons.calendar_today, color: AppColors.orange),
                          const SizedBox(width: 12),
                          Text(
                            _formatDate(_selectedDate),
                            style: Theme.of(context).textTheme.bodyMedium,
                          ),
                          const Spacer(),
                          const Icon(Icons.arrow_drop_down, color: AppColors.mediumGrey),
                        ],
                      ),
                    ),
                  ),
                  if (_dateHasError)
                    Padding(
                      padding: const EdgeInsets.only(top: 8, left: 12),
                      child: Text(
                        'La fecha no puede ser anterior a hoy',
                        style: Theme.of(context).textTheme.bodySmall?.copyWith(
                          color: Colors.red,
                        ),
                      ),
                    ),
                ],
              ),
            ),

            const SizedBox(height: 16),

            // TIPO
            Container(
              padding: const EdgeInsets.all(20),
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
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Tipo',
                    style: Theme.of(context).textTheme.titleMedium?.copyWith(
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  const SizedBox(height: 12),
                  DropdownButtonFormField<EventType>(
                    initialValue: _selectedType,
                    decoration: const InputDecoration(
                      prefixIcon: Icon(Icons.category, color: AppColors.orange),
                    ),
                    items: EventType.values.map((type) {
                      return DropdownMenuItem(
                        value: type,
                        child: Text(type.displayName),
                      );
                    }).toList(),
                    onChanged: (EventType? newValue) {
                      if (newValue != null) {
                        setState(() {
                          _selectedType = newValue;
                        });
                      }
                    },
                    validator: (EventType? value) {
                      if (value == null) {
                        return 'Debe seleccionar un tipo de evento';
                      }
                      return null;
                    },
                  ),
                ],
              ),
            ),

            const SizedBox(height: 32),

            // BOTÓN GUARDAR
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: _saveEvent,
                style: ElevatedButton.styleFrom(
                  backgroundColor: AppColors.orange,
                  foregroundColor: AppColors.white,
                  padding: const EdgeInsets.symmetric(vertical: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                ),
                child: const Text(
                  'Guardar Evento',
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w600,
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  String _formatDate(DateTime date) {
    const months = [
      'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
      'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
    ];
    return '${date.day} de ${months[date.month - 1]}, ${date.year}';
  }
}

class _CustomDatePicker extends StatefulWidget {
  final DateTime initialDate;
  final Function(DateTime) onDateSelected;

  const _CustomDatePicker({
    required this.initialDate,
    required this.onDateSelected,
  });

  @override
  State<_CustomDatePicker> createState() => _CustomDatePickerState();
}

class _CustomDatePickerState extends State<_CustomDatePicker> {
  late DateTime _selectedDate;
  late DateTime _displayedMonth;

  @override
  void initState() {
    super.initState();
    _selectedDate = widget.initialDate;
    _displayedMonth = DateTime(_selectedDate.year, _selectedDate.month, 1);
  }

  @override
  Widget build(BuildContext context) {
    return Dialog(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Container(
        padding: const EdgeInsets.all(20),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            // Header
            Text(
              'Seleccionar fecha',
              style: Theme.of(context).textTheme.titleLarge?.copyWith(
                fontWeight: FontWeight.w600,
              ),
            ),
            const SizedBox(height: 20),
            
            // Month navigation
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                IconButton(
                  onPressed: () {
                    setState(() {
                      _displayedMonth = DateTime(_displayedMonth.year, _displayedMonth.month - 1, 1);
                    });
                  },
                  icon: const Icon(Icons.chevron_left),
                ),
                Text(
                  _formatMonthYear(_displayedMonth),
                  style: Theme.of(context).textTheme.titleMedium?.copyWith(
                    fontWeight: FontWeight.w600,
                  ),
                ),
                IconButton(
                  onPressed: () {
                    setState(() {
                      _displayedMonth = DateTime(_displayedMonth.year, _displayedMonth.month + 1, 1);
                    });
                  },
                  icon: const Icon(Icons.chevron_right),
                ),
              ],
            ),
            
            const SizedBox(height: 16),
            
            // Days of week
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb']
                  .map((day) => SizedBox(
                        width: 32,
                        child: Text(
                          day,
                          textAlign: TextAlign.center,
                          style: Theme.of(context).textTheme.bodySmall?.copyWith(
                            fontWeight: FontWeight.w600,
                            color: AppColors.mediumGrey,
                          ),
                        ),
                      ))
                  .toList(),
            ),
            
            const SizedBox(height: 8),
            
            // Calendar grid
            _buildCalendarGrid(),
            
            const SizedBox(height: 20),
            
            // Buttons
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                TextButton(
                  onPressed: () => Navigator.pop(context),
                  child: const Text(
                    'Cancelar',
                    style: TextStyle(color: AppColors.mediumGrey),
                  ),
                ),
                const SizedBox(width: 8),
                ElevatedButton(
                  onPressed: () {
                    widget.onDateSelected(_selectedDate);
                    Navigator.pop(context);
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: AppColors.orange,
                    foregroundColor: AppColors.white,
                  ),
                  child: const Text('Aceptar'),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildCalendarGrid() {
    final firstDayOfMonth = DateTime(_displayedMonth.year, _displayedMonth.month, 1);
    final lastDayOfMonth = DateTime(_displayedMonth.year, _displayedMonth.month + 1, 0);
    final firstDayWeekday = firstDayOfMonth.weekday % 7; // 0 = Sunday
    
    final days = <Widget>[];
    
    // Empty cells for days before the first day of the month
    for (int i = 0; i < firstDayWeekday; i++) {
      days.add(const SizedBox(width: 32, height: 32));
    }
    
    // Days of the month
    for (int day = 1; day <= lastDayOfMonth.day; day++) {
      final date = DateTime(_displayedMonth.year, _displayedMonth.month, day);
      final isSelected = date.day == _selectedDate.day &&
          date.month == _selectedDate.month &&
          date.year == _selectedDate.year;
      final isToday = date.day == DateTime.now().day &&
          date.month == DateTime.now().month &&
          date.year == DateTime.now().year;
      final isPast = date.isBefore(DateTime.now().subtract(const Duration(days: 1)));
      
      days.add(
        GestureDetector(
          onTap: isPast ? null : () {
            setState(() {
              _selectedDate = date;
            });
          },
          child: Container(
            width: 32,
            height: 32,
            margin: const EdgeInsets.all(2),
            decoration: BoxDecoration(
              color: isSelected
                  ? AppColors.orange
                  : isToday
                      ? AppColors.orange.withOpacity(0.2)
                      : null,
              borderRadius: BorderRadius.circular(16),
              border: isToday && !isSelected
                  ? Border.all(color: AppColors.orange)
                  : null,
            ),
            child: Center(
              child: Text(
                '$day',
                style: TextStyle(
                  color: isPast
                      ? AppColors.mediumGrey
                      : isSelected
                          ? AppColors.white
                          : AppColors.black,
                  fontWeight: isSelected || isToday ? FontWeight.w600 : FontWeight.normal,
                ),
              ),
            ),
          ),
        ),
      );
    }
    
    return Wrap(
      children: days,
    );
  }

  String _formatMonthYear(DateTime date) {
    const months = [
      'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
      'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
    ];
    return '${months[date.month - 1]} ${date.year}';
  }
}
