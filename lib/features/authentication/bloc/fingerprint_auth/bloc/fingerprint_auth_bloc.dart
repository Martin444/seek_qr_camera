import 'package:flutter_bloc/flutter_bloc.dart';

import '../../../data/repository/biometric_auth_repository.dart';
import '../../authentication_bloc.dart';

class FingerprintAuthBloc extends Bloc<FingerprintAuthEvent, FingerprintAuthState> {
  final BiometricAuthRepository repository;

  FingerprintAuthBloc({required this.repository}) : super(FingerprintAuthInitial()) {
    on<FingerprintAuthRequested>(_onAuthenticationRequested);
  }

  Future<void> _onAuthenticationRequested(FingerprintAuthRequested event, Emitter<FingerprintAuthState> emit) async {
    emit(FingerprintAuthLoading());
    try {
      final response = await repository.authenticate(event.promptMessage);
      emit(FingerprintAuthSuccess(response));
      event.navigator.pushReplacementNamed('/home');
    } catch (e) {
      emit(FingerprintAuthFailure(e.toString()));
    }
  }
}
