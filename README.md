# seek_qr_camera

Prueba tecnica para seek usando patrón BLoc e integración Nativa.

# Proyecto Dart: Configuraciones de VSCode

Este proyecto utiliza configuraciones personalizadas en VSCode para lanzar la aplicación Dart en diferentes entornos. Las configuraciones están definidas en el archivo `.vscode/launch.json` y permiten iniciar la aplicación con parámetros específicos para distintos escenarios.

## Configuraciones disponibles

### sk_dev (Desarrollo)
- **Archivo de programa:** `lib/main.dart`
- **Argumentos:** `--dart-define=PIN=3001`
- **Uso:** Configuración ideal para entornos de desarrollo. Selecciona esta configuración desde el menú de depuración en VSCode para iniciar la aplicación en modo de desarrollo con el PIN configurado a `3001`.

### sk_qa (Calidad)
- **Archivo de programa:** `lib/main.dart`
- **Argumentos:** `--dart-define=PIN=1234`
- **Uso:** Configuración destinada a entornos de calidad (QA). Usa este perfil para pruebas que requieran el PIN establecido a `1234`.

## Cómo utilizar estas configuraciones

1. **Abrir el proyecto en VSCode:** Asegúrate de tener instalado el plugin de Dart y, si es necesario, el de Flutter.
2. **Seleccionar la configuración de depuración:** Abre la pestaña de **Run and Debug** (Depurar) en VSCode. Haz clic en el selector de configuraciones y elige `sk_dev` o `sk_qa` según el entorno deseado.
3. **Iniciar la depuración:** Presiona el botón de "Play" (Iniciar) o utiliza el atajo correspondiente para comenzar la ejecución de la aplicación con la configuración seleccionada.

## Personalización

- **Modificar argumentos y variables de entorno:** Puedes añadir o cambiar los argumentos en la sección `args` de cada configuración en `.vscode/launch.json` para ajustarse a tus necesidades.
- **Ajuste del archivo de programa:** Si el archivo principal de la aplicación no se encuentra en `lib/main.dart`, cambia la propiedad `"program"` para apuntar a la ruta correcta.

## Recursos adicionales

- [Documentación de VSCode para Dart](https://go.microsoft.com/fwlink/?linkid=830387): Encuentra más información sobre las posibles configuraciones y uso de variables de entorno en aplicaciones Dart.

Este README proporciona una guía rápida para entender y utilizar las configuraciones disponibles en este proyecto. ¡Disfruta programando!