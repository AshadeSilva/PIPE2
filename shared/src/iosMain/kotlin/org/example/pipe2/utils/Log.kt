package org.example.pipe2.utils
actual fun logDebug(tag: String, message: String) {
    println("ASHA_DEBUG $tag: $message")
}

actual fun logDebugE(tag: String, e: Exception) {
    println("ASHA_DEBUG $tag: $e.message")
}
