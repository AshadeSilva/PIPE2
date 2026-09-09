package org.example.pipe2.data.account.local

import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.account.UserDetails

interface LocalAccountDB {

    suspend fun updateDetails(details: UserDetails)
    suspend fun removeUser(uid: String)
    suspend fun getAccount(email: String, password: String): String
    suspend fun getUserDocument(uid: String): Flow<UserDetails?>
}