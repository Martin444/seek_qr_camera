import 'package:equatable/equatable.dart';

/// Eventos definidos para el CameraBloc.
abstract class CameraEvent extends Equatable {
  const CameraEvent();

  @override
  List<Object?> get props => [];
}

/// Evento que solicita abrir la cámara con determinados parámetros.
class OpenCameraEvent extends CameraEvent {
  final Map<String, dynamic> arguments;

  const OpenCameraEvent({required this.arguments});

  @override
  List<Object?> get props => [arguments];
}
