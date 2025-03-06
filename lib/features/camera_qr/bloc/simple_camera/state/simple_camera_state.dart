import 'package:equatable/equatable.dart';

/// Estados definidos para el CameraBloc.
abstract class CameraState extends Equatable {
  const CameraState();

  @override
  List<Object?> get props => [];
}

/// Estado inicial antes de realizar alguna acción.
class CameraInitial extends CameraState {}

/// Estado en el que se está realizando la operación.
class CameraLoading extends CameraState {}

/// Estado cuando la operación se completa exitosamente.
class CameraSuccess extends CameraState {
  final Map<String, dynamic>? response;

  const CameraSuccess({this.response});

  @override
  List<Object?> get props => [response];
}

/// Estado cuando ocurre un error durante la operación.
class CameraFailure extends CameraState {
  final String error;

  const CameraFailure({required this.error});

  @override
  List<Object?> get props => [error];
}
