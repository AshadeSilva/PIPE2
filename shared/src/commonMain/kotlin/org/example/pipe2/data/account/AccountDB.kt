package org.example.pipe2.data.account

interface AccountDB {
    val currentDetails: DBResult?
    suspend fun signInSignUp(email: String, password: String)
    suspend fun signOut()
}