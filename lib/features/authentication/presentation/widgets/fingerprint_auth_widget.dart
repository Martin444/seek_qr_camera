import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../bloc/authentication_bloc.dart';

class FingerprintAuthButton extends StatelessWidget {
  const FingerprintAuthButton({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocConsumer<FingerprintAuthBloc, FingerprintAuthState>(
      listener: (context, state) {
        if (state is FingerprintAuthSuccess) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text('Autenticación exitosa')),
          );
        } else if (state is FingerprintAuthFailure) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text(state.errorMessage)),
          );
        }
      },
      builder: (context, state) {
        // Puedes personalizar el comportamiento según el estado.
        return TextButton(
          onPressed: () {
            // Envía el evento para solicitar autenticación
            context.read<FingerprintAuthBloc>().add(
                  FingerprintAuthRequested(
                    promptMessage: "Verifica tu huella digital",
                    navigator: Navigator.of(context),
                  ),
                );
          },
          child: Row(
            mainAxisSize: MainAxisSize.min,
            children: const [
              Icon(Icons.fingerprint),
              SizedBox(width: 8),
              Text('Usar huella digital'),
            ],
          ),
        );
      },
    );
  }
}
