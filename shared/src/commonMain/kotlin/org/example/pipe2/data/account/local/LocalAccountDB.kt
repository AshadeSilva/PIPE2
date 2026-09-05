package org.example.pipe2.data.account.local

import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.account.UserDetails

interface LocalAccountDB {

    // probably needs a forget user
    suspend fun updateDetails(details: UserDetails)
    suspend fun removeUser(uid: String)

    // authenticate user signing in to the remote database. Sign in only, not sign up
    suspend fun getAccount(email: String, password: String): String
    suspend fun getUserDocument(uid: String): Flow<UserDetails?>

    // remember sign in between app sessions
    suspend fun rememberUser(uid: String)
    suspend fun forgetUser()
    suspend fun getUser(): String?
}