package com.camera.qr.seek.seek_qr_camera

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.Executor

// Ensure that BiometricAuthApi interface declares:
// suspend fun authenticate(request: AuthRequest): AuthResponse

class BiometricAuthManager(private val context: Context, private val executor: Executor) : BiometricAuthApi {

    override suspend fun authenticate(request: AuthRequest): AuthResponse = try {
        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Authentication")
                    .setSubtitle(request.promptMessage)
                    .setNegativeButtonText("Cancel")
                    .build()

                // Use coroutineScope to encapsulate asynchronous operation.
                coroutineScope {
                    val deferred = CompletableDeferred<AuthResponse>()
                    val activity = context as? FragmentActivity
                        ?: return@coroutineScope AuthResponse(false, "Context is not a FragmentActivity")
                    val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                            deferred.complete(AuthResponse(false, "Authentication error: $errString"))
                        }
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            deferred.complete(AuthResponse(true, null))
                        }
                        override fun onAuthenticationFailed() {
                            deferred.complete(AuthResponse(false, "Authentication failed"))
                        }
                    })
                    biometricPrompt.authenticate(promptInfo)
                    deferred.await()
                }
            }
            else -> {
                AuthResponse(false, "Biometric authentication not available")
            }
        }
    } catch (e: Exception) {
        AuthResponse(false, "Error: ${e.message}")
    }
}