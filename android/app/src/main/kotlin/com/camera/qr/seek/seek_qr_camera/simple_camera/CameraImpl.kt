package com.camera.qr.seek.seek_qr_cam.simple_camera
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import android.app.Activity
import androidx.core.app.ActivityCompat
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

sealed class CameraState {
    object Starting : CameraState()
    object Started : CameraState()
    data class Error(val message: String?) : CameraState()
}

class CameraImpl(private val context: Context, private val lifecycleOwner: LifecycleOwner) {

    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    fun initFlow(): Flow<CameraState> = callbackFlow {
        cameraExecutor = Executors.newSingleThreadExecutor()
        trySend(CameraState.Starting)
        if (!allPermissionsGranted()) {
            requestPermissions()
            trySend(CameraState.Error("Permisos no concedidos"))
            close()
        } else {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                try {
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    cameraProvider.unbindAll()
                    imageCapture = ImageCapture.Builder().build()
                    cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, imageCapture)
                    trySend(CameraState.Started)
                } catch (exc: Exception) {
                    Toast.makeText(context, "Error al iniciar la cámara: ${exc.message}", Toast.LENGTH_SHORT).show()
                    trySend(CameraState.Error("Error al iniciar la cámara: ${exc.message}"))
                }
                close() // cerramos el flow una vez emitido el estado
            }, ContextCompat.getMainExecutor(context))
        }
        awaitClose { /* Se pueden liberar recursos si es necesario */ }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                imageCapture = ImageCapture.Builder().build()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, imageCapture)

            } catch (exc: Exception) {
                Toast.makeText(context, "Error al iniciar la cámara: ${exc.message}", Toast.LENGTH_SHORT).show()
            }

        }, ContextCompat.getMainExecutor(context))
    }

    fun takePhoto(outputDirectory: File, onImageSaved: (File) -> Unit) {
        val capture = imageCapture ?: return

        val photoFile = File(
            outputDirectory,
            "${System.currentTimeMillis()}.jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        capture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(context, "Error al capturar imagen: ${exc.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    onImageSaved(photoFile)
                }
            })
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private fun requestPermissions() {
        if (context is Activity) {
            ActivityCompat.requestPermissions(
                context,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        } else {
            Toast.makeText(context, "No se puede solicitar permisos sin una Activity", Toast.LENGTH_SHORT).show()
        }
    }
}
