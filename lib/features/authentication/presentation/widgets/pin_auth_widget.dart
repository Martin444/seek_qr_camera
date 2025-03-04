import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:pinput/pinput.dart';

import '../../bloc/authentication_bloc.dart';

class PinAuthWidget extends StatelessWidget {
  const PinAuthWidget({super.key, required this.bloc});

  final PinAuthBloc bloc;

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<PinAuthBloc, PinAuthState>(
      bloc: bloc,
      builder: (context, state) {
        return Column(
          children: [
            Pinput(
              length: 4,
              defaultPinTheme: PinTheme(
                width: 50,
                height: 50,
                decoration: BoxDecoration(
                  border: Border.all(color: Colors.black),
                  borderRadius: BorderRadius.circular(10),
                ),
              ),
              onCompleted: (pin) => bloc.add(PinAuthSubmitted(pin: pin, navigator: Navigator.of(context))),
              onChanged: (pin) => bloc.add(PinAuthChanged(pin: pin)),
            ),
            if (state.errorMessage != null)
              Text(
                state.errorMessage!,
                style: const TextStyle(color: Colors.red),
              ),
          ],
        );
      },
    );
  }
}
