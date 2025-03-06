import 'package:flutter/material.dart';
import 'package:seek_qr_camera/features/authentication/bloc/pin_auth/bloc/pin_auth_bloc.dart';
import 'package:seek_qr_camera/features/authentication/presentation/widgets/fingerprint_auth_widget.dart';

import '../widgets/pin_auth_widget.dart';

class LoginPage extends StatelessWidget {
  const LoginPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        width: double.infinity,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
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
            FingerprintAuthButton()
          ],
        ),
      ),
    );
  }
}
