package org.example.pipe2.data.account.local

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.example.pipe2.data.account.UserDetails
import org.example.pipe2.logic.account.User


class RoomAccountDB(val room: AccountDAO): LocalAccountDB {

    override suspend fun updateDetails(details: UserDetails) = room.upsert(details)

    override suspend fun removeUserDocument(uid: String) = room.deleteByUid(uid)

    // TODO: eventually need an actual password system
    override suspend fun authenticate(email: String, password: String): String {
        val details = room.getByEmail(email)
        return details?.uid ?: throw UserNotFoundError(email)
    }

    override suspend fun getUserDocument(uid: String): Flow<UserDetails?> = room.selectByUid(uid)
}