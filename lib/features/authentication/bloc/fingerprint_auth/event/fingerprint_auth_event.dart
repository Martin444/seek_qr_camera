import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';

/// Evento base para la autenticación biométrica.
abstract class FingerprintAuthEvent extends Equatable {
  const FingerprintAuthEvent();

  @override
  List<Object?> get props => [];
}

/// Evento para solicitar la autenticación biométrica, enviando un promptMessage.
class FingerprintAuthRequested extends FingerprintAuthEvent {
  final String promptMessage;
  final NavigatorState navigator;

  const FingerprintAuthRequested({required this.promptMessage, required this.navigator});

  @override
  List<Object?> get props => [promptMessage];
}
