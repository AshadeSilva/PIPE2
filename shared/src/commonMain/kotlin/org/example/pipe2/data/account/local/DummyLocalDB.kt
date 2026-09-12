package org.example.pipe2.data.account.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.example.pipe2.data.account.UserDetails
import org.example.pipe2.data.account.UserNotFoundError

class DummyLocalDB: LocalAccountDB {

    private val _account =
        MutableStateFlow<UserDetails?>(null)

    override suspend fun clearUser() {
        _account.value = null
    }

    override suspend fun getUserDocument(): Flow<UserDetails?> =
        _account

    override suspend fun getOnce(): UserDetails? = _account.value

    override suspend fun updateDetails(details: UserDetails) {
        _account.value = details
    }
}