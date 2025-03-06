import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:seek_qr_camera/core/channels/biometric_auth_channel.dart';
import 'package:seek_qr_camera/features/authentication/bloc/authentication_bloc.dart';
import 'package:seek_qr_camera/features/authentication/data/repository/biometric_auth_repository.dart';
import 'package:seek_qr_camera/features/authentication/presentation/pages/login_page.dart';
import 'package:seek_qr_camera/features/home/presentation/page/home_page.dart';

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
          create: (context) => PinAuthBloc(),
        ),
        BlocProvider<FingerprintAuthBloc>(
          create: (context) => FingerprintAuthBloc(repository: BiometricAuthRepository(BiometricAuthChannel())),
        ),
      ],
      child: MaterialApp(
        title: 'Flutter Demo',
        debugShowCheckedModeBanner: false,
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
          useMaterial3: true,
        ),
        initialRoute: '/',
        routes: {
          '/': (context) => LoginPage(),
          '/home': (context) => HomePage(), // Define tus rutas aquí
        },
      ),
    );
  }
}
