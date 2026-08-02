package org.example.pipe2.data.account

import kotlinx.coroutines.flow.StateFlow

interface LocalAccountDB {

    // probably needs a forget user
    fun updateDetails(details: UserDetails)
    fun removeUserDocument(uid: String)

    // authenticate user signing in to the remote database. Sign in only, not sign up
    fun authenticate(email: String, password: String): String
    fun getUserDocument(uid: String): StateFlow<UserDetails>?
}

class UserNotFoundError(val email: String) : Exception() {
    override val message: String
        get() = "user is not in system: $email"
}

class InvalidCredentialsError(val email: String) : Exception() {
    override val message: String
        get() = "invalid credentials: $email"
}

class CorruptedAccountError(val uid: String): Exception() {
    override val message: String
        get() = "account mising crucial details"
}