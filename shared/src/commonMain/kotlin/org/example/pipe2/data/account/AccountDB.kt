package org.example.pipe2.data.account

import org.example.pipe2.logic.DBResult
import org.example.pipe2.logic.UserContext

interface AccountDB {
    val currentDetails: DBResult?
    suspend fun signInSignUp(email: String, password: String)
    suspend fun signOut()
}