package org.example.pipe2.data.account

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.String

class DummyLocalAccountDB: LocalAccountDB {

    private val savedUsers: MutableMap<String, UserDocument> = mutableMapOf()

    class UserDocument(
        initial: UserDetails
    ) {
        private val _details = MutableStateFlow(initial)
        val details: StateFlow<UserDetails> = _details.asStateFlow()

        fun update(transform: (UserDetails) -> UserDetails) {
            _details.update(transform)
        }
    }

    override fun removeUserDocument(uid: String) {
        savedUsers.remove(uid)
    }

    override fun getUserDocument(uid: String): StateFlow<UserDetails>? =
        savedUsers.get(uid)?.details

    // TODO: eventually need an actual password system
    override fun authenticate(email: String, password: String): String =
        savedUsers.values.firstOrNull {it.details.value.email == email}?.details?.value?.uid
            ?: throw UserNotFoundError(email)


    // TODO: currently this updates whichever user has the uid given. Should it only work for the signed in user?
    override fun updateDetails(details: UserDetails) {
        val user = savedUsers.getOrPut(details.uid) { UserDocument(details) }
        user.update { details }
    }
}