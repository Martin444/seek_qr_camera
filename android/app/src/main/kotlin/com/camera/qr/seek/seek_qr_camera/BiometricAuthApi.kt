// Autogenerated from Pigeon (v24.2.1), do not edit directly.
// See also: https://pub.dev/packages/pigeon
@file:Suppress("UNCHECKED_CAST", "ArrayInDataClass")

package com.camera.qr.seek.seek_qr_camera

import android.util.Log
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugin.common.StandardMethodCodec
import io.flutter.plugin.common.StandardMessageCodec
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import kotlinx.coroutines.runBlocking

private fun wrapResult(result: Any?): List<Any?> {
  return listOf(result)
}

private fun wrapError(exception: Throwable): List<Any?> {
  return if (exception is FlutterError) {
    listOf(
      exception.code,
      exception.message,
      exception.details
    )
  } else {
    listOf(
      exception.javaClass.simpleName,
      exception.toString(),
      "Cause: " + exception.cause + ", Stacktrace: " + Log.getStackTraceString(exception)
    )
  }
}

/**
 * Error class for passing custom error details to Flutter via a thrown PlatformException.
 * @property code The error code.
 * @property message The error message.
 * @property details The error details. Must be a datatype supported by the api codec.
 */
class FlutterError (
  val code: String,
  override val message: String? = null,
  val details: Any? = null
) : Throwable()

/** Generated class from Pigeon that represents data sent in messages. */
data class AuthRequest (
  val promptMessage: String? = null
)
 {
  companion object {
    fun fromList(pigeonVar_list: List<Any?>): AuthRequest {
      val promptMessage = pigeonVar_list[0] as String?
      return AuthRequest(promptMessage)
    }
  }
  fun toList(): List<Any?> {
    return listOf(
      promptMessage,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class AuthResponse (
  val success: Boolean? = null,
  val errorMessage: String? = null
)
 {
  companion object {
    fun fromList(pigeonVar_list: List<Any?>): AuthResponse {
      val success = pigeonVar_list[0] as Boolean?
      val errorMessage = pigeonVar_list[1] as String?
      return AuthResponse(success, errorMessage)
    }
  }
  fun toList(): List<Any?> {
    return listOf(
      success,
      errorMessage,
    )
  }
}
private open class BiometricAuthApiPigeonCodec : StandardMessageCodec() {
  override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
    return when (type) {
      129.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          AuthRequest.fromList(it)
        }
      }
      130.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          AuthResponse.fromList(it)
        }
      }
      else -> super.readValueOfType(type, buffer)
    }
  }
  override fun writeValue(stream: ByteArrayOutputStream, value: Any?)   {
    when (value) {
      is AuthRequest -> {
        stream.write(129)
        writeValue(stream, value.toList())
      }
      is AuthResponse -> {
        stream.write(130)
        writeValue(stream, value.toList())
      }
      else -> super.writeValue(stream, value)
    }
  }
}

/** Generated interface from Pigeon that represents a handler of messages from Flutter. */
interface BiometricAuthApi {
  suspend fun authenticate(request: AuthRequest): AuthResponse

  companion object {
    /** The codec used by BiometricAuthApi. */
    val codec: MessageCodec<Any?> by lazy {
      BiometricAuthApiPigeonCodec()
    }
    /** Sets up an instance of `BiometricAuthApi` to handle messages through the `binaryMessenger`. */
    @JvmOverloads
    fun setUp(binaryMessenger: BinaryMessenger, api: BiometricAuthApi?, messageChannelSuffix: String = "") {
      val separatedMessageChannelSuffix = if (messageChannelSuffix.isNotEmpty()) ".$messageChannelSuffix" else ""
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.seek_qr_camera.BiometricAuthApi.authenticate$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val requestArg = args[0] as AuthRequest
            val wrapped: List<Any?> = try {
              listOf(runBlocking { api.authenticate(requestArg) })
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
    }
  }
}
