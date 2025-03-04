import 'package:pigeon/pigeon.dart';

class AuthRequest {
  String? promptMessage;
}

class AuthResponse {
  bool? success;
  String? errorMessage;
}

@HostApi()
abstract class BiometricAuthApi {
  AuthResponse authenticate(AuthRequest request);
}
