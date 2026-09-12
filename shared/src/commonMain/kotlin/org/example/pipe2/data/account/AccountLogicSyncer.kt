package org.example.pipe2.data.account

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.pipe2.data.account.local.LocalAccountDB
import org.example.pipe2.logic.user.User
import org.example.pipe2.logic.user.toUser

class AccountLogicSyncer(val local: LocalAccountDB) {
    suspend fun observeUser(uid: String): Flow<User?> =
        local.getUserDocument().map { details ->
            details?.let {
                if (it.uid != uid) {
                    throw CorruptedAccountError(uid)
                }
                it.toUser()
            }
        }

    suspend fun getAccount(email: String, password: String): String {
        val details = local.getOnce()
        if (details?.email == null) {
            throw UserNotFoundError(email)
        } else {
            //TODO: some kind of actual authentification
            return details.uid
        }
        // might throw an incorrectPassword if real
    }

    suspend fun lastUid(): String? = local.getOnce()?.uid
}
