package org.example.pipe2.data.account.local

import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.account.UserDetails

interface LocalAccountDB {
    suspend fun updateDetails(details: UserDetails)
    suspend fun clearUser()
    suspend fun getUserDocument(): Flow<UserDetails?>
    suspend fun getOnce(): UserDetails?
}