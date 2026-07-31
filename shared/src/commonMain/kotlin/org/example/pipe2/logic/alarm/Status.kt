package org.example.pipe2.logic.alarm

enum class Status (val message: String) {
    SAFELY_EVACUATED_WARDEN("safely evacuated - registered by warden"),
    OFFSITE("offsite"),
    ALERT("ALERT"),
    PENDING("")
}

fun String.toStatus(): Status? = Status.entries.find { it.message == this }