package org.example.pipe2.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.example.pipe2.data.account.UserDetails
import org.example.pipe2.data.account.UserNotFoundError
import org.example.pipe2.data.account.local.LocalAccountDB
import org.example.pipe2.data.auth.local.LocalAuthDB
import org.example.pipe2.data.auth.local.LoggedIn

class DummyLocalDB: LocalAccountDB, LocalAuthDB {

    private var currentUser: LoggedIn? = null

    private val _accounts =
        MutableStateFlow<Map<String, UserDetails>>(emptyMap())

    override suspend fun removeUser(uid: String) {
        _accounts.value = _accounts.value.filter { it.key != uid }
    }

    override suspend fun getUserDocument(uid: String): Flow<UserDetails?> =
        _accounts.map { map -> map[uid] }

    // a pretend system. always true if exists
    override suspend fun getAccount(email: String, password: String): String {
        val details = _accounts.value.values.filter { it.email == email }.firstOrNull()
        return details?.uid ?: throw UserNotFoundError(email)
    }

    override suspend fun updateDetails(details: UserDetails) {
        _accounts.value = _accounts.value.mapValues {
            (key, value) -> if (key == details.uid) {details} else {value}
        }
    }

    override suspend fun rememberUser(uid: String) {
        currentUser = LoggedIn(uid)
    }
    override suspend fun forgetUser() {
        currentUser = null
    }
    override suspend fun getUser(): String? = currentUser?.uid
}