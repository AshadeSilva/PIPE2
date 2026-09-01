package org.example.pipe2.data.account.local

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