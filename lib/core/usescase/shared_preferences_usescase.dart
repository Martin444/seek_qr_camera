import '../channels/shared_preferences_channel.dart';

/// Repositorio que encapsula el acceso a las preferencias encriptadas utilizando
/// el canal nativo implementado en [SharedPreferencesChannel].
class EncryptedPreferencesUsescases {
  // Instancia del canal para comunicación nativa.
  final SharedPreferencesChannel _channel;

  EncryptedPreferencesUsescases({SharedPreferencesChannel? channel}) : _channel = channel ?? SharedPreferencesChannel();

  /// Recupera el valor encriptado asociado a [key].
  ///
  /// Se espera que el código nativo retorne un Map que contenga la clave 'value'.
  Future<String?> getEncryptedString(String key) async {
    // Invoca el método nativo y captura la respuesta.
    final Map<String, dynamic>? result = await _channel.invokeMethod('getEncryptedString', {'key': key});

    // Se espera que el Map contenga la clave 'value'. Si no se encuentra,
    // se retorna null.
    if (result?.containsKey('value') ?? false) {
      return result?['value'] as String?;
    }
    return null;
  }

  /// Guarda [value] usando la llave [key] en las preferencias encriptadas.
  ///
  /// Se invoca el método nativo y se espera una respuesta de éxito.
  Future<void> setEncryptedString(String key, String value) async {
    await _channel.invokeMethod('setEncryptedString', {'key': key, 'value': value});
  }
}
