# seek_qr_camera

Prueba técnica para seek utilizando el patrón BLoC e integración nativa.

---

## Configuraciones VSCode

El proyecto incluye varias configuraciones personalizadas para ejecutar y compilar la aplicación:

- **sk_dev (Desarrollo)**
  - Programa: `lib/main.dart`
  - Argumentos: `--dart-define=PIN=3001`

- **sk_qa (Calidad)**
  - Programa: `lib/main.dart`
  - Argumentos: `--dart-define=PIN=1234`

- **sk_build_apk (Compilación Release)**
  - Programa: `lib/main.dart`
  - Modo: Release
  - Argumentos: `--dart-define=PIN=3001`

---

## Uso

1. Abre el proyecto en VSCode (asegúrate de tener instalados los plugins de Dart y Flutter).
2. Ve a la pestaña **Run and Debug**.
3. Selecciona la configuración deseada (sk_dev, sk_qa o sk_build_apk) y ejecuta la acción correspondiente.

---

## Estructura del Proyecto

El código se organiza de la siguiente manera:

- **lib/**
  - **core/**: Lógica central, conexión a los CHANNELS nativos de Android, middlewares y casos de uso (ej. autenticación biométrica y manejo de preferencias).
  - **feature/**
    - **authentication/**: Autenticación (huella y PIN) con BLoC.
    - **camera_qr/**: Funcionalidad de cámara y códigos QR (lógica y vistas).
    - **home/**: Página principal.
  - **routes/**: Definición de rutas de la aplicación.
  - `main.dart`: Punto de entrada.
  - `pigeon.dart`: Comunicación entre Flutter y código nativo.

- **Código Nativo (Android)**
  - **auth/**: 
    - `AuthModel.kt`: Modelo de autenticación.
    - `BiometricAuthApi.kt`: API para autenticación biométrica.
    - `BiometricAuthManager.kt`: Gestión de autenticación biométrica.
  - **shared_preferences/**:  
    - `EncryptedSharedPreferences.kt`: Implementación de preferencias encriptadas.
  - **simple_camera/**:
    - `CameraImpl.kt`: Lógica de la cámara.
    - `CameraPreviewView.kt`: Vista de previsualización de la cámara.
    - `CameraPreviewViewFactory.kt`: Fábrica para la vista de la cámara.
  - `MainActivity.kt`: Actividad principal que integra Flutter y el código nativo.

---

## Integración de Platform Views

La aplicación utiliza Platform Views para integrar vistas nativas (como la previsualización de la cámara mediante AndroidView) en Flutter. La configuración se realiza en el archivo `MainActivity.kt`, donde se registran estas vistas.

---

## Personalización

- Puedes modificar los argumentos y ajustes en `.vscode/launch.json` según el entorno.
- Si cambias el punto de entrada, ajusta la ruta del archivo en la configuración de VSCode.

---

¡Disfruta desarrollando y probando la aplicación!