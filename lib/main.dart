import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:seek_qr_camera/core/channels/biometric_auth_channel.dart';
import 'package:seek_qr_camera/core/channels/shared_preferences_channel.dart';
import 'package:seek_qr_camera/core/middleware/route_middleware.dart';
import 'package:seek_qr_camera/core/usescase/shared_preferences_usescase.dart';
import 'package:seek_qr_camera/features/authentication/bloc/authentication_bloc.dart';
import 'package:seek_qr_camera/features/authentication/data/repository/biometric_auth_repository.dart';
import 'package:seek_qr_camera/features/camera_qr/bloc/simple_camera/bloc/simple_camera_bloc.dart';
import 'package:seek_qr_camera/features/camera_qr/data/repository/camera_repository.dart';
import 'package:seek_qr_camera/routes/app_routes.dart';

void main() {
  SystemChrome.setSystemUIOverlayStyle(
    const SystemUiOverlayStyle(
      statusBarColor: Colors.transparent,
      statusBarIconBrightness: Brightness.dark,
    ),
  );
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider<PinAuthBloc>(
          create: (context) => PinAuthBloc(
            EncryptedPreferencesUsescases(
              channel: SharedPreferencesChannel(),
            ),
          ),
        ),
        BlocProvider<FingerprintAuthBloc>(
          create: (context) => FingerprintAuthBloc(
            repository: BiometricAuthRepository(
              BiometricAuthChannel(),
            ),
          ),
        ),
        BlocProvider<CameraBloc>(
          create: (context) => CameraBloc(
            cameraRepository: CameraRepository(),
          ),
        ),
      ],
      child: MaterialApp(
        title: 'Seek prueba tecnica',
        debugShowCheckedModeBanner: false,
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
          useMaterial3: true,
        ),
        initialRoute: AppRoutes.HOME,
        onGenerateRoute: RouteMiddleware().onGenerateRoute,
      ),
    );
  }
}
