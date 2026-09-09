package org.example.pipe2.data.auth.remote

interface RemoteAuth {

    val uid: String?
    suspend fun signIn(email: String, password: String): String?
    suspend fun signOut()
}
