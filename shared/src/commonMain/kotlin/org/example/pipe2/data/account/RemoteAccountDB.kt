package org.example.pipe2.data.account

interface RemoteAccountDB {
    val currentDetails: UserDetails?
    suspend fun signIn(email: String, password: String)
    suspend fun signOut()
}