package org.example.pipe2.data.account

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

class UserNotRemote(): Exception("Could not find user in remote db")
class ForceSignOut(m: String?): Exception(m)
class NotAuthenticated(): Exception()
class IncorrectPassword(): Exception("remote auth failed because of incorrect password")
class RemoteAuthFailed(): Exception()