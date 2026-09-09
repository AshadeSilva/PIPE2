package org.example.pipe2.data.auth.local

import org.example.pipe2.data.account.UserDetails

interface LocalAuthDB {
    // remember sign in between app sessions
    suspend fun rememberUser(uid: String)
    suspend fun forgetUser()
    suspend fun getUser(): String?
}