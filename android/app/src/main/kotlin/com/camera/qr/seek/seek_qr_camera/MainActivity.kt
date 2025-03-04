package com.camera.qr.seek.seek_qr_camera

import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.camera.qr.seek.seek_qr_camera/biometric_auth"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            if (call.method == "authenticate") {
                val api = BiometricAuthApi(this)
                val request = AuthRequest().apply {
                    promptMessage = call.argument<String>("promptMessage")
                }
                val response = api.authenticate(request)
                result.success(response.toMap())
            } else {
                result.notImplemented()
            }
        }
    }
}
