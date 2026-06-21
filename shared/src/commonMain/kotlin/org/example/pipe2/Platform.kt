package org.example.pipe2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform