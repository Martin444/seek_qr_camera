class AuthResponseModel {
  final bool success;
  final String errorMessage;

  AuthResponseModel({required this.success, required this.errorMessage});

  factory AuthResponseModel.fromMap(Map<String, dynamic> map) {
    return AuthResponseModel(
      success: map['success'] ?? false,
      errorMessage: map['errorMessage'] ?? 'Unknown error',
    );
  }
}
