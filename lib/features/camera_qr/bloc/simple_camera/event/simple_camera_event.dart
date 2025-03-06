import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';

/// Eventos definidos para el CameraBloc.
abstract class CameraEvent extends Equatable {
  const CameraEvent();

  @override
  List<Object?> get props => [];
}

/// Evento que solicita abrir la cámara con determinados parámetros.
class OpenCameraEvent extends CameraEvent {
  final Map<String, dynamic> arguments;
  final NavigatorState navigator;

  const OpenCameraEvent({required this.arguments, required this.navigator});

  @override
  List<Object?> get props => [arguments];
}
