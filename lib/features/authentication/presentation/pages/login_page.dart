import 'package:flutter/material.dart';
import 'package:seek_qr_camera/features/authentication/bloc/pin_auth/bloc/pin_auth_bloc.dart';

import '../widgets/pin_auth_widget.dart';

class LoginPage extends StatelessWidget {
  const LoginPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          SizedBox(
            height: 120,
          ),
          PinAuthWidget(
            bloc: PinAuthBloc(),
          ),
          SizedBox(
            height: 30,
          ),
          Row(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              TextButton(
                onPressed: () {},
                child: Row(
                  children: [
                    Icon(Icons.fingerprint),
                    Text('Usar huella digital'),
                  ],
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
