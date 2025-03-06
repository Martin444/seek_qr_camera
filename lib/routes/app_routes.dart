// ignore_for_file: constant_identifier_names

import 'package:flutter/material.dart';
import 'package:seek_qr_camera/features/authentication/presentation/pages/login_page.dart';
import 'package:seek_qr_camera/features/home/presentation/page/home_page.dart';

class AppRoutes {
  static const String HOME = '/';
  static const String LOGIN = '/login';

  static Route<dynamic>? generateRoute(RouteSettings settings) {
    switch (settings.name) {
      case HOME:
        return MaterialPageRoute(builder: (_) => HomePage());

      case LOGIN:
        return MaterialPageRoute(builder: (_) => LoginPage());
      default:
        return MaterialPageRoute(builder: (_) => HomePage());
    }
  }
}
