package org.example.pipe2.data.account.local

import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.account.UserDetails


class RoomAccountDB(private val account: AccountDAO): LocalAccountDB {
    override suspend fun updateDetails(details: UserDetails) {
        clearUser()
        account.upsert(details)
    }
    override suspend fun clearUser() = account.clear()
    override suspend fun getUserDocument(): Flow<UserDetails?> =
        account.getFlow()
    override suspend fun getOnce(): UserDetails? = account.getOnce()

}