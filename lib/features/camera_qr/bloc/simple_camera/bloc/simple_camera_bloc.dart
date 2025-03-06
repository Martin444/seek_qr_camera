import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:seek_qr_camera/features/camera_qr/data/repository/camera_repository.dart';

import '../simple_camera.dart';

/// Bloc encargado de manejar la lógica asociada a la cámara utilizando el CameraRepository.
class CameraBloc extends Bloc<CameraEvent, CameraState> {
  final CameraRepository cameraRepository;

  CameraBloc({required this.cameraRepository}) : super(CameraInitial()) {
    on<OpenCameraEvent>(_onOpenCamera);
  }

  Future<void> _onOpenCamera(OpenCameraEvent event, Emitter<CameraState> emit) async {
    emit(CameraLoading());
    try {
      final response = await cameraRepository.openCamera(event.arguments);
      emit(CameraSuccess(response: response));
    } catch (e) {
      emit(CameraFailure(error: e.toString()));
    }
  }
}
