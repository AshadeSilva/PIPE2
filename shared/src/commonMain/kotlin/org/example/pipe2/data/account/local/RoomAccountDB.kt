package org.example.pipe2.data.account.local

import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.RoomDB
import org.example.pipe2.data.account.UserDetails
import org.example.pipe2.data.account.UserNotFoundError


class RoomAccountDB(room: RoomDB): LocalAccountDB {
    private val account = room.accountDao()
    override suspend fun updateDetails(details: UserDetails) = account.upsert(details)
    override suspend fun removeUser(uid: String) = account.deleteByUid(uid)
    // TODO: eventually need an actual password system
    override suspend fun getAccount(email: String, password: String): String {
        val details = account.getByEmail(email)
        return details?.uid ?: throw UserNotFoundError(email)
        // might throw an incorrectPassword if real
    }
    override suspend fun getUserDocument(uid: String): Flow<UserDetails?> = account.selectByUid(uid)
}