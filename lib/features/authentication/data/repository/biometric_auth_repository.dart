// Define una interfaz para el canal de autenticación biométrica
import 'package:seek_qr_camera/core/channels/biometric_auth_channel.dart';

import '../models/auth_response_model.dart';

// Clase BiometricAuthRepository
class BiometricAuthRepository {
  final IBiometricAuthChannel _channel;

  BiometricAuthRepository(this._channel);

  Future<AuthResponseModel> authenticate(String promptMessage) async {
    try {
      final result = await _channel.invokeMethod('authenticate', {'promptMessage': promptMessage});
      return AuthResponseModel.fromMap(result);
    } catch (e) {
      throw Exception('Error al autenticar: $e');
    }
  }
}
