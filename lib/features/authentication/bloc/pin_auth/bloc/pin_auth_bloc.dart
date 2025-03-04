import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:seek_qr_camera/features/authentication/bloc/authentication_bloc.dart';

class PinAuthBloc extends Bloc<PinAuthEvent, PinAuthState> {
  PinAuthBloc() : super(const PinAuthState()) {
    on<PinAuthChanged>(_onPinputChanged);
    on<PinAuthSubmitted>(_onPinputSubmitted);
  }

  void _onPinputChanged(PinAuthChanged event, Emitter<PinAuthState> emit) {
    emit(state.copyWith(
      pin: event.pin,
      errorMessage: null,
      isValid: false,
    ));
  }

  void _onPinputSubmitted(PinAuthSubmitted event, Emitter<PinAuthState> emit) async {
    if (event.pin == '1234') {
      emit(state.copyWith(
        pin: event.pin,
        isValid: true,
        errorMessage: null,
      ));
      // Navegar a la próxima pantalla

      event.navigator.pushReplacementNamed('/home');
    } else {
      emit(state.copyWith(
        pin: event.pin,
        isValid: false,
        errorMessage: 'PIN incorrecto',
      ));
    }
  }
}
