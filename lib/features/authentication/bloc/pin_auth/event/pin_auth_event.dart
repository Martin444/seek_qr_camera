import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';

class PinAuthEvent extends Equatable {
  const PinAuthEvent();

  @override
  List<Object?> get props => [];
}

class PinAuthChanged extends PinAuthEvent {
  final String pin;

  const PinAuthChanged({required this.pin});

  @override
  List<Object?> get props => [pin];
}

class PinAuthSubmitted extends PinAuthEvent {
  final String pin;
  final NavigatorState navigator;

  const PinAuthSubmitted({required this.pin, required this.navigator});

  @override
  List<Object?> get props => [pin];
}
