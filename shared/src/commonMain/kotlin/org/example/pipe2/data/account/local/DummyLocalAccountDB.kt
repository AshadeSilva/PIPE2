package org.example.pipe2.data.account.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.example.pipe2.data.account.UserDetails
import kotlin.String

class DummyLocalAccountDB: LocalAccountDB {

    private val _accounts =
        MutableStateFlow<Map<String, UserDetails>>(emptyMap())

    override suspend fun removeUserDocument(uid: String) {
        _accounts.value = _accounts.value.filter { it.key != uid }
    }

    override suspend fun getUserDocument(uid: String): Flow<UserDetails?> =
        _accounts.map { map -> map[uid] }

    // a pretend system. always true if exists
    override suspend fun authenticate(email: String, password: String): String {
        val details = _accounts.value.values.filter { it.email == email }.firstOrNull()
        return details?.uid ?: throw UserNotFoundError(email)
    }

    override suspend fun updateDetails(details: UserDetails) {
        _accounts.value = _accounts.value.mapValues {
            (key, value) -> if (key == details.uid) {details} else {value}
        }
    }
}