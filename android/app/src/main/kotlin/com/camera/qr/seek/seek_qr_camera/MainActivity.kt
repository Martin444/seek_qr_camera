package com.camera.qr.seek.seek_qr_camera
import com.camera.qr.seek.seek_qr_cam.simple_camera.CameraImpl
import com.camera.qr.seek.seek_qr_cam.simple_camera.CameraState

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.core.content.ContextCompat
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodCall
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.content.SharedPreferences

class MainActivity : FlutterFragmentActivity() {
    private val biometricChannelId = "com.camera.qr.seek.seek_qr_camera/biometric_auth"
    private val encryptedChannelId = "com.camera.qr.seek.seek_qr_camera/shared_preferences"
    private val cameraChannelId = "com.camera.qr.seek.seek_qr_camera/camera"
    private val TAG = "MainActivity"

    private lateinit var encryptedSharedPreferences: SharedPreferences
    private lateinit var cameraHandler: CameraImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initEncryptedSharedPreferences()
        // Inicializar CameraImpl
        cameraHandler = CameraImpl(this, this)
    }
    

    private fun initEncryptedSharedPreferences() {
        val spec = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        val masterKey = MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyGenParameterSpec(spec)
            .build()

        encryptedSharedPreferences = EncryptedSharedPreferences.create(
            this,
            "encrypted_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, biometricChannelId).setMethodCallHandler { call, result ->
            when (call.method) {
                "authenticate" -> authenticate(call, result)
                else -> result.notImplemented()
            }
        }

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, encryptedChannelId).setMethodCallHandler { call, result ->
            when (call.method) {
                "getEncryptedString" -> getEncryptedString(call, result)
                "setEncryptedString" -> setEncryptedString(call, result)
                else -> result.notImplemented()
            }
        }

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, cameraChannelId).setMethodCallHandler { call, result ->
            when (call.method) {
                "openCamera" -> {
                    // Lanzamos una coroutine para recolectar el Flow
                    CoroutineScope(Dispatchers.Main).launch {
                        // Esperamos a que el flow emita un estado que no sea Starting (es decir, un estado final)
                        val state = cameraHandler.initFlow().first { it !is CameraState.Starting }
                        when (state) {
                            is CameraState.Started -> result.success("Camera started")
                            is CameraState.Error -> result.error("CAMERA_ERROR", state.message, null)
                            else -> result.error("UNKNOWN_STATE", "Estado inesperado", null)
                        }
                    }
                }
                else -> result.notImplemented()
            }
        }
    }

    private fun authenticate(call: MethodCall, result: MethodChannel.Result) {
        Log.d(TAG, "Método authenticate llamado desde Flutter")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permiso de biometría no concedido")
            result.error("PERMISSION_DENIED", "Biometric permission not granted", null)
            return
        }
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
    }

    private fun getEncryptedString(call: MethodCall, result: MethodChannel.Result) {
        val key = call.argument<String>("key")
        val value = encryptedSharedPreferences.getString(key, null)
        result.success(value)
    }

    private fun setEncryptedString(call: MethodCall, result: MethodChannel.Result) {
        val key = call.argument<String>("key")
        val value = call.argument<String>("value")
        encryptedSharedPreferences.edit().putString(key, value).apply()
        result.success(value)
    }
}