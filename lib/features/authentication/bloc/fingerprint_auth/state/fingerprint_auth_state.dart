import 'package:equatable/equatable.dart';

import '../../../data/models/auth_response_model.dart';

/// Estado base para la autenticación biométrica.
abstract class FingerprintAuthState extends Equatable {
  const FingerprintAuthState();

  @override
  List<Object?> get props => [];
}

/// Estado inicial.
class FingerprintAuthInitial extends FingerprintAuthState {}

/// Estado de carga mientras se procesa la autenticación.
class FingerprintAuthLoading extends FingerprintAuthState {}

/// Estado de éxito que contiene el modelo de respuesta.
class FingerprintAuthSuccess extends FingerprintAuthState {
  final AuthResponseModel response;

  const FingerprintAuthSuccess(this.response);

  @override
  List<Object?> get props => [response];
}

/// Estado de fallo, contiene un mensaje de error.
class FingerprintAuthFailure extends FingerprintAuthState {
  final String errorMessage;

  const FingerprintAuthFailure(this.errorMessage);

  @override
  List<Object?> get props => [errorMessage];
}
