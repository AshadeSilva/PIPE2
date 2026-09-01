package org.example.pipe2.data.account.remote

import kotlinx.coroutines.flow.StateFlow
import org.example.pipe2.data.account.UserDetails

interface RemoteAccountDB {
    val currentDetails: StateFlow<UserDetails?>
    suspend fun signIn(email: String, password: String): UserDetails
    suspend fun signOut()
}
