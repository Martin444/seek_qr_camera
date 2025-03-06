import 'package:flutter/material.dart';

import '../../routes/app_routes.dart';

abstract class RouteMiddlewareImpl {
  Route<dynamic>? handleRoute(RouteSettings settings);

  Route<dynamic>? onGenerateRoute(RouteSettings settings) {
    Route<dynamic>? route = handleRoute(settings);
    if (route != null) {
      return route;
    }
    return AppRoutes.generateRoute(settings);
  }
}
