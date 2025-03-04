package com.camera.qr.seek.seek_qr_camera

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.core.content.ContextCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.runBlocking

class MainActivity : FlutterActivity() {
    private val CHANNEL = "com.camera.qr.seek.seek_qr_camera/biometric_auth"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            if (call.method == "authenticate") {
                // Create AuthRequest with promptMessage directly
                val promptMessage = call.argument<String>("promptMessage") ?: ""
                val request = AuthRequest(promptMessage)

                val biometricAuthManager = BiometricAuthManager(this, ContextCompat.getMainExecutor(this))
                val response = runBlocking { biometricAuthManager.authenticate(request) }
                result.success(response.toList())
            } else {
                result.notImplemented()
            }
        }
    }
}