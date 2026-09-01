package org.example.pipe2.data.account.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import org.example.pipe2.data.account.UserDetails

interface LocalAccountDB {

    // probably needs a forget user
    suspend fun updateDetails(details: UserDetails)
    suspend fun removeUserDocument(uid: String)

    // authenticate user signing in to the remote database. Sign in only, not sign up
    suspend fun authenticate(email: String, password: String): String
    suspend fun getUserDocument(uid: String): Flow<UserDetails?>
}