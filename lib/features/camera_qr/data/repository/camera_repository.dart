import 'package:seek_qr_camera/core/channels/camera_channel.dart';

/// Repository encargado de encapsular las llamadas al canal de cámara.
class CameraRepository {
  final CameraChannel _cameraChannel;

  CameraRepository({CameraChannel? cameraChannel}) : _cameraChannel = cameraChannel ?? CameraChannel();

  /// Invoca el método 'openCamera' a través del canal nativo.
  /// [arguments] representa los parámetros requeridos para la apertura de la cámara.
  /// Retorna un Map<String, dynamic> con la respuesta del código nativo o null en caso de no obtener respuesta válida.
  Future<Map<String, dynamic>?> openCamera(Map<String, dynamic> arguments) async {
    try {
      return await _cameraChannel.invokeMethod('openCamera', arguments);
    } catch (e) {
      // Se puede adaptar la gestión de errores según sea necesario
      throw Exception("Error al invocar openCamera: $e");
    }
  }
}
