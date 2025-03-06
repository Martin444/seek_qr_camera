import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:seek_qr_camera/core/config.dart';
import 'package:seek_qr_camera/core/usescase/shared_preferences_usescase.dart';
import 'package:seek_qr_camera/features/authentication/bloc/authentication_bloc.dart';

class PinAuthBloc extends Bloc<PinAuthEvent, PinAuthState> {
  final EncryptedPreferencesUsescases encryptedPreferences;
  PinAuthBloc(this.encryptedPreferences) : super(const PinAuthState()) {
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
    if (event.pin == PIN_ACCESS) {
      ACCESS_TOKEN = event.pin;
      // Al autenticar exitosamente, persistir el ACCESS_TOKEN en preferencias encriptadas.
      await encryptedPreferences.setEncryptedString("ACCESS_TOKEN", event.pin);
      emit(state.copyWith(
        pin: event.pin,
        isValid: true,
        errorMessage: null,
      ));

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
