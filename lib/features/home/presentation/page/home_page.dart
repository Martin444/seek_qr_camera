import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../../camera_qr/bloc/simple_camera/simple_camera.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    // Generamos una lista de textos para mostrar en el body
    final List<String> items = List.generate(20, (index) => 'Item ${index + 1}');

    return Scaffold(
      appBar: AppBar(
        title: const Text('Seek QR Camera'),
        actions: [
          IconButton(
            icon: const Icon(Icons.exit_to_app),
            tooltip: 'Cerrar sesión',
            onPressed: () {
              // Aquí se puede implementar la lógica de cierre de sesión.
              // Por ejemplo, se puede limpiar el estado global y navegar a la pantalla de login.
              Navigator.pushReplacementNamed(context, '/login');
            },
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          context.read<CameraBloc>().add(
                OpenCameraEvent(
                  arguments: {},
                  navigator: Navigator.of(context),
                ),
              );
        },
        child: Icon(Icons.qr_code),
      ),
      body: ListView.builder(
        padding: const EdgeInsets.all(16.0),
        itemCount: items.length,
        itemBuilder: (BuildContext context, int index) {
          return Padding(
            padding: const EdgeInsets.symmetric(vertical: 8.0),
            child: Text(
              items[index],
              style: const TextStyle(fontSize: 18),
            ),
          );
        },
      ),
    );
  }
}
