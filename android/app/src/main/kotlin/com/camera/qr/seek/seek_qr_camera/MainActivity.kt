package com.camera.qr.seek.seek_qr_camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.core.content.ContextCompat
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.runBlocking

class MainActivity : FlutterFragmentActivity() {
    private val CHANNEL = "com.camera.qr.seek.seek_qr_camera/biometric_auth"
    private val TAG = "MainActivity"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            if (call.method == "authenticate") {
                Log.d(TAG, "Método authenticate llamado desde Flutter")
                // Verificar que se tenga el permiso de biometría
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permiso de biometría no concedido")
                    result.error("PERMISSION_DENIED", "Biometric permission not granted", null)
                    return@setMethodCallHandler
                }
                // Create AuthRequest with promptMessage directly
                val promptMessage = call.argument<String>("promptMessage") ?: ""
                Log.d(TAG, "PromptMessage recibido: $promptMessage")
                val request = AuthRequest(promptMessage)

                val biometricAuthManager = BiometricAuthManager(this, ContextCompat.getMainExecutor(this))
                try {
                    Log.d(TAG, "Iniciando autenticación biométrica")
                    val response = runBlocking { biometricAuthManager.authenticate(request) }
                    Log.d(TAG, "Respuesta de autenticación: $response")
                    result.success(response.toList())
                    Log.d(TAG, "Respuesta enviada a Flutter exitosamente")
                } catch (e: Exception) {
                    Log.d(TAG, "Error durante la autenticación: ${e.message}")
                    result.error("AUTH_ERROR", "Error during biometric authentication: ${e.message}", null)
                }
            } else {
                result.notImplemented()
            }
        }
    }
}