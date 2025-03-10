import 'package:flutter/services.dart';
import 'package:seek_qr_camera/core/channels/global_channel_impl.dart';

// Implementación estándar utilizando MethodChannel
class BiometricAuthChannel implements GlobalChannelImpl {
  static const MethodChannel _channel = MethodChannel('com.camera.qr.seek.seek_qr_camera/biometric_auth');

  @override
  Future<Map<String, dynamic>> invokeMethod(String method, Map<String, dynamic> arguments) async {
    final result = await _channel.invokeMethod(method, arguments);
    if (result is Map) {
      return Map<String, dynamic>.from(result);
    } else {
      throw Exception('Respuesta inválida del código nativo. $result');
    }
  }
}
