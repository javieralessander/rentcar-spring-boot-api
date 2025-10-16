# Guía de Conexión Flutter - RentCar API

Esta guía te ayudará a conectar tu aplicación Flutter con la API RentCar.

## 1. Configuración Inicial

### Agregar dependencia HTTP en Flutter

En tu archivo `pubspec.yaml`:

```yaml
dependencies:
  flutter:
    sdk: flutter
  http: ^1.1.0  # Para peticiones HTTP
  # Otras dependencias...
```

Ejecuta: `flutter pub get`

## 2. Configuración de la API

### Crear un servicio API

Crea el archivo `lib/services/api_service.dart`:

```dart
import 'dart:convert';
import 'package:http/http.dart' as http;

class ApiService {
  static const String baseUrl = 'http://localhost:8080/api';

  // Headers comunes
  static const Map<String, String> headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
  };

  // GET request genérico
  static Future<dynamic> get(String endpoint) async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl$endpoint'),
        headers: headers,
      );

      if (response.statusCode == 200) {
        return json.decode(response.body);
      } else {
        throw Exception('Error: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error de conexión: $e');
    }
  }

  // POST request genérico
  static Future<dynamic> post(String endpoint, Map<String, dynamic> data) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl$endpoint'),
        headers: headers,
        body: json.encode(data),
      );

      if (response.statusCode == 200 || response.statusCode == 201) {
        return json.decode(response.body);
      } else {
        throw Exception('Error: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error de conexión: $e');
    }
  }

  // PUT request genérico
  static Future<dynamic> put(String endpoint, Map<String, dynamic> data) async {
    try {
      final response = await http.put(
        Uri.parse('$baseUrl$endpoint'),
        headers: headers,
        body: json.encode(data),
      );

      if (response.statusCode == 200) {
        return json.decode(response.body);
      } else {
        throw Exception('Error: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error de conexión: $e');
    }
  }

  // DELETE request genérico
  static Future<bool> delete(String endpoint) async {
    try {
      final response = await http.delete(
        Uri.parse('$baseUrl$endpoint'),
        headers: headers,
      );

      return response.statusCode == 200;
    } catch (e) {
      throw Exception('Error de conexión: $e');
    }
  }
}
```

## 3. Modelos de Datos

### Modelo Cliente

Crea `lib/models/cliente.dart`:

```dart
class Cliente {
  final int? id;
  final String nombre;
  final String cedula;
  final String noTarjetaCr;
  final double limiteCredito;
  final String tipoPersona; // 'FISICA' o 'JURIDICA'
  final bool estado;

  Cliente({
    this.id,
    required this.nombre,
    required this.cedula,
    required this.noTarjetaCr,
    required this.limiteCredito,
    required this.tipoPersona,
    this.estado = true,
  });

  factory Cliente.fromJson(Map<String, dynamic> json) {
    return Cliente(
      id: json['id'],
      nombre: json['nombre'],
      cedula: json['cedula'],
      noTarjetaCr: json['noTarjetaCr'],
      limiteCredito: json['limiteCredito'].toDouble(),
      tipoPersona: json['tipoPersona'],
      estado: json['estado'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'nombre': nombre,
      'cedula': cedula,
      'noTarjetaCr': noTarjetaCr,
      'limiteCredito': limiteCredito,
      'tipoPersona': tipoPersona,
      'estado': estado,
    };
  }
}
```

### Modelo Vehículo

Crea `lib/models/vehiculo.dart`:

```dart
class Vehiculo {
  final int? id;
  final String descripcion;
  final String noChasis;
  final String noMotor;
  final String noPlaca;
  final Map<String, dynamic>? tipoVehiculo;
  final Map<String, dynamic>? marca;
  final Map<String, dynamic>? modelo;
  final Map<String, dynamic>? tipoCombustible;
  final bool estado;

  Vehiculo({
    this.id,
    required this.descripcion,
    required this.noChasis,
    required this.noMotor,
    required this.noPlaca,
    this.tipoVehiculo,
    this.marca,
    this.modelo,
    this.tipoCombustible,
    this.estado = true,
  });

  factory Vehiculo.fromJson(Map<String, dynamic> json) {
    return Vehiculo(
      id: json['id'],
      descripcion: json['descripcion'],
      noChasis: json['noChasis'],
      noMotor: json['noMotor'],
      noPlaca: json['noPlaca'],
      tipoVehiculo: json['tipoVehiculo'],
      marca: json['marca'],
      modelo: json['modelo'],
      tipoCombustible: json['tipoCombustible'],
      estado: json['estado'],
    );
  }
}
```

## 4. Servicios Específicos

### Servicio de Clientes

Crea `lib/services/cliente_service.dart`:

```dart
import '../models/cliente.dart';
import 'api_service.dart';

class ClienteService {
  static Future<List<Cliente>> getClientes() async {
    try {
      final response = await ApiService.get('/clientes');
      return (response as List)
          .map((item) => Cliente.fromJson(item))
          .toList();
    } catch (e) {
      throw Exception('Error al obtener clientes: $e');
    }
  }

  static Future<Cliente> createCliente(Cliente cliente) async {
    try {
      final response = await ApiService.post('/clientes', cliente.toJson());
      return Cliente.fromJson(response);
    } catch (e) {
      throw Exception('Error al crear cliente: $e');
    }
  }

  static Future<Cliente?> getClienteById(int id) async {
    try {
      final response = await ApiService.get('/clientes/$id');
      return Cliente.fromJson(response);
    } catch (e) {
      print('Error al obtener cliente: $e');
      return null;
    }
  }

  static Future<bool> updateCliente(int id, Cliente cliente) async {
    try {
      await ApiService.put('/clientes/$id', cliente.toJson());
      return true;
    } catch (e) {
      print('Error al actualizar cliente: $e');
      return false;
    }
  }

  static Future<bool> deleteCliente(int id) async {
    try {
      return await ApiService.delete('/clientes/$id');
    } catch (e) {
      print('Error al eliminar cliente: $e');
      return false;
    }
  }
}
```

### Servicio de Vehículos

Crea `lib/services/vehiculo_service.dart`:

```dart
import '../models/vehiculo.dart';
import 'api_service.dart';

class VehiculoService {
  static Future<List<Vehiculo>> getVehiculos() async {
    try {
      final response = await ApiService.get('/vehiculos');
      return (response as List)
          .map((item) => Vehiculo.fromJson(item))
          .toList();
    } catch (e) {
      throw Exception('Error al obtener vehículos: $e');
    }
  }

  static Future<List<Vehiculo>> getVehiculosDisponibles() async {
    try {
      final response = await ApiService.get('/vehiculos/disponibles');
      return (response as List)
          .map((item) => Vehiculo.fromJson(item))
          .toList();
    } catch (e) {
      throw Exception('Error al obtener vehículos disponibles: $e');
    }
  }

  // Obtener catálogos
  static Future<List<Map<String, dynamic>>> getTiposVehiculos() async {
    try {
      final response = await ApiService.get('/tipos-vehiculos');
      return List<Map<String, dynamic>>.from(response);
    } catch (e) {
      throw Exception('Error al obtener tipos de vehículos: $e');
    }
  }

  static Future<List<Map<String, dynamic>>> getMarcas() async {
    try {
      final response = await ApiService.get('/marcas');
      return List<Map<String, dynamic>>.from(response);
    } catch (e) {
      throw Exception('Error al obtener marcas: $e');
    }
  }
}
```

## 5. Ejemplo de Uso en Widget

### Pantalla de Lista de Clientes

```dart
import 'package:flutter/material.dart';
import '../models/cliente.dart';
import '../services/cliente_service.dart';

class ClientesScreen extends StatefulWidget {
  @override
  _ClientesScreenState createState() => _ClientesScreenState();
}

class _ClientesScreenState extends State<ClientesScreen> {
  List<Cliente> clientes = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    loadClientes();
  }

  Future<void> loadClientes() async {
    try {
      final result = await ClienteService.getClientes();
      setState(() {
        clientes = result;
        isLoading = false;
      });
    } catch (e) {
      setState(() {
        isLoading = false;
      });
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Clientes'),
        actions: [
          IconButton(
            icon: Icon(Icons.add),
            onPressed: () {
              // Navegar a pantalla de crear cliente
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => CreateClienteScreen()),
              ).then((_) => loadClientes());
            },
          ),
        ],
      ),
      body: isLoading
          ? Center(child: CircularProgressIndicator())
          : ListView.builder(
              itemCount: clientes.length,
              itemBuilder: (context, index) {
                final cliente = clientes[index];
                return ListTile(
                  title: Text(cliente.nombre),
                  subtitle: Text('Cédula: ${cliente.cedula}'),
                  trailing: Text('${cliente.tipoPersona}'),
                  onTap: () {
                    // Navegar a detalles del cliente
                  },
                );
              },
            ),
    );
  }
}
```

## 6. Configuración de Red (Android)

En `android/app/src/main/AndroidManifest.xml`, agrega:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<application
    android:usesCleartextTraffic="true"
    ... >
```

## 7. Configuración para iOS

En `ios/Runner/Info.plist`, agrega:

```xml
<key>NSAppTransportSecurity</key>
<dict>
    <key>NSAllowsArbitraryLoads</key>
    <true/>
</dict>
```

## 8. Endpoints Disponibles

```dart
// Clientes
GET    /api/clientes
POST   /api/clientes
GET    /api/clientes/{id}
PUT    /api/clientes/{id}
DELETE /api/clientes/{id}

// Vehículos
GET    /api/vehiculos
GET    /api/vehiculos/disponibles
POST   /api/vehiculos
PUT    /api/vehiculos/{id}

// Rentas
GET    /api/rentas
POST   /api/rentas
PUT    /api/rentas/{id}/devolver
GET    /api/rentas/reporte?fechaInicio=2023-01-01&fechaFin=2023-12-31

// Catálogos
GET    /api/tipos-vehiculos
GET    /api/marcas
GET    /api/modelos
GET    /api/tipos-combustibles

// Inspecciones
GET    /api/inspecciones
POST   /api/inspecciones
GET    /api/inspecciones/vehiculo/{id}
```

## 9. Manejo de Errores

```dart
try {
  final clientes = await ClienteService.getClientes();
  // Manejar datos exitosos
} catch (e) {
  // Manejar error
  if (e.toString().contains('conexión')) {
    // Error de conexión
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('Error de Conexión'),
        content: Text('Verifica que la API esté ejecutándose en http://localhost:8080'),
      ),
    );
  }
}
```

## 10. Ejemplo de Formulario

```dart
// Crear cliente
final cliente = Cliente(
  nombre: nombreController.text,
  cedula: cedulaController.text,
  noTarjetaCr: tarjetaController.text,
  limiteCredito: double.parse(limiteController.text),
  tipoPersona: tipoPersonaSeleccionado, // 'FISICA' o 'JURIDICA'
);

try {
  await ClienteService.createCliente(cliente);
  Navigator.pop(context);
} catch (e) {
  // Manejar error
}
```

Esta guía te proporciona todo lo necesario para conectar tu aplicación Flutter con la API RentCar. La estructura es simple y funcional para empezar a desarrollar rápidamente.