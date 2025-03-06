package com.camera.qr.seek.seek_qr_cam.simple_camera

import android.content.Context
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import io.flutter.plugin.platform.PlatformView
import com.google.common.util.concurrent.ListenableFuture
import androidx.lifecycle.LifecycleOwner

class CameraPreviewView(private val context: Context) : PlatformView {
    private val previewView: PreviewView = PreviewView(context)
    private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
        ProcessCameraProvider.getInstance(context)

    init {
        bindPreview()
    }

    private fun bindPreview() {
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                // Configuramos el preview usando CameraX
                val preview: Preview = Preview.Builder().build()
                preview.setSurfaceProvider(previewView.surfaceProvider)
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                // Limpiar cualquier binding previo y enlazar a lifecycle.
                cameraProvider.unbindAll()
                // Se asume que el context es una Activity que implementa LifecycleOwner
                cameraProvider.bindToLifecycle(context as LifecycleOwner, cameraSelector, preview)
            } catch (exc: Exception) {
                // Puedes implementar control de errores según sea necesario
                exc.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(context))
    }

    override fun getView(): View = previewView

    override fun dispose() {
        // Liberar recursos, si fuese necesario.
    }
}