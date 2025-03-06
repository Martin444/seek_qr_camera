import 'package:flutter/material.dart';
import 'package:seek_qr_camera/core/middleware/route_middleware_impl.dart';
import 'package:seek_qr_camera/features/authentication/presentation/pages/login_page.dart';

import '../config.dart';

class RouteMiddleware extends RouteMiddlewareImpl {
  @override
  Route<dynamic>? handleRoute(RouteSettings settings) {
    if (ACCESS_TOKEN.isEmpty) {
      return MaterialPageRoute(builder: (_) => LoginPage());
    }
    return null;
  }
}
