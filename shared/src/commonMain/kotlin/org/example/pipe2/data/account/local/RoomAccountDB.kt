package org.example.pipe2.data.account.local

import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.account.UserDetails
import org.example.pipe2.data.account.UserNotFoundError


class RoomAccountDB(val room: AccountDB): LocalAccountDB {
    private val account = room.accountDao()
    private val loggedIn = room.loggedInDao()

    override suspend fun updateDetails(details: UserDetails) = account.upsert(details)
    override suspend fun removeUser(uid: String) = account.deleteByUid(uid)
    // TODO: eventually need an actual password system
    override suspend fun getAccount(email: String, password: String): String {
        val details = account.getByEmail(email)
        return details?.uid ?: throw UserNotFoundError(email)
        // might throw an incorrectPassword if real
    }
    override suspend fun getUserDocument(uid: String): Flow<UserDetails?> = account.selectByUid(uid)

    override suspend fun rememberUser(uid: String) = loggedIn.rememberUser(LoggedIn(uid))
    override suspend fun forgetUser() = loggedIn.forgetUser()
    override suspend fun getUser(): String? = loggedIn.getUser()?.uid
}