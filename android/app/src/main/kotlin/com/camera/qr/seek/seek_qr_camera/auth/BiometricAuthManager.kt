package com.camera.qr.seek.seek_qr_camera

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.Executor
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

// Ensure that BiometricAuthApi interface declares:
// suspend fun authenticate(request: AuthRequest): AuthResponse

class BiometricAuthManager(private val context: Context, private val executor: Executor) : BiometricAuthApi {

    private val TAG = "BiometricAuthManager"

    override suspend fun authenticate(request: AuthRequest): AuthResponse = try {
        Log.d(TAG, "Iniciando proceso de autenticación biométrica")
        val biometricManager = BiometricManager.from(context)
        val authResult = when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> handleBiometricAuthentication(request)
            else -> {
                Log.d(TAG, "Autenticación biométrica no disponible: ${biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)}")
                AuthResponse(false, "Biometric authentication not available")
            }
        }
        authResult
    } catch (e: Exception) {
        Log.e(TAG, "Error en la autenticación: ${e.message}", e)
        AuthResponse(false, "Error: ${e.message}")
    }

    private suspend fun handleBiometricAuthentication(request: AuthRequest): AuthResponse {
        val timeoutMillis = 10000L // Tiempo de espera ajustable según necesidades (en milisegundos)
        return withTimeoutOrNull(timeoutMillis) {
            suspendCancellableCoroutine { cont ->
                val activity = context as? FragmentActivity
                if (activity == null) {
                    Log.d(TAG, "El contexto no es un FragmentActivity")
                    cont.resume(AuthResponse(false, "Context is not a FragmentActivity"))
                    return@suspendCancellableCoroutine
                }

                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Authentication")
                    .setSubtitle(request.promptMessage)
                    .setNegativeButtonText("Cancel")
                    .build()

                val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        Log.d(TAG, "Callback onAuthenticationError: $errString (code: $errorCode)")
                        if (!cont.isCompleted) {
                            cont.resume(AuthResponse(false, "Authentication error: $errString"))
                        }
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        Log.d(TAG, "Callback onAuthenticationSucceeded invoked")
                        if (!cont.isCompleted) {
                            cont.resume(AuthResponse(true, null))
                        }
                    }

                    override fun onAuthenticationFailed() {
                        Log.d(TAG, "Callback onAuthenticationFailed invoked")
                        if (!cont.isCompleted) {
                            cont.resume(AuthResponse(false, "Authentication failed"))
                        }
                    }
                })

                Log.d(TAG, "Llamando a biometricPrompt.authenticate(promptInfo)")
                biometricPrompt.authenticate(promptInfo)
            }
        } ?: run {
            Log.d(TAG, "Timeout: no se obtuvo respuesta dentro de $timeoutMillis ms")
            AuthResponse(false, "Authentication timeout")
        }
    }

}