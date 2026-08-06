package org.example.pipe2.data.account

import kotlinx.coroutines.flow.StateFlow

interface RemoteAccountDB {
    val currentDetails: StateFlow<UserDetails?>
    suspend fun signIn(email: String, password: String): UserDetails
    suspend fun signOut()
}
