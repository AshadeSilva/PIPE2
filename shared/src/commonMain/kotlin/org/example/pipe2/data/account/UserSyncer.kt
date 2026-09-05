package org.example.pipe2.data.account

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.pipe2.data.account.local.LocalAccountDB
import org.example.pipe2.logic.user.User
import org.example.pipe2.logic.user.toUser
import org.example.pipe2.utils.logDebug

class UserSyncer(val local: LocalAccountDB) {
    suspend fun observeUser(uid: String): Flow<User?> =
        local.getUserDocument(uid).map { details ->
            details?.let {
                if (it.uid != uid) {
                    throw CorruptedAccountError(uid)
                }
                logDebug("ASHADEBUG", "local updating details with ${it.email}")
                it.toUser()
            }
        }
}
