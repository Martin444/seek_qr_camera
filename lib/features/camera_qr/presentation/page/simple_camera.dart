import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class SimpleCamera extends StatelessWidget {
  const SimpleCamera({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(children: [
        Expanded(
          child: AndroidView(
            viewType: 'camera_preview',
            layoutDirection: TextDirection.ltr,
            creationParams: <String, dynamic>{},
            creationParamsCodec: const StandardMessageCodec(),
          ),
        ),
      ]),
    );
  }
}
