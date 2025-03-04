import 'package:equatable/equatable.dart';

class PinAuthState extends Equatable {
  final String pin;
  final bool isValid;
  final String? errorMessage;

  const PinAuthState({
    this.pin = '',
    this.isValid = false,
    this.errorMessage,
  });

  PinAuthState copyWith({
    String? pin,
    bool? isValid,
    String? errorMessage,
  }) {
    return PinAuthState(
      pin: pin ?? this.pin,
      isValid: isValid ?? this.isValid,
      errorMessage: errorMessage ?? this.errorMessage,
    );
  }

  @override
  List<Object?> get props => [pin, isValid, errorMessage];
}
