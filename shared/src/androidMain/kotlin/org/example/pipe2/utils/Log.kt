package org.example.pipe2.utils
import android.util.Log

actual fun logDebug(tag: String, message: String) {
    Log.d(tag, message)
}

actual fun logDebugE(tag: String, e: Exception) {
    Log.e(tag, e.message, e)
}