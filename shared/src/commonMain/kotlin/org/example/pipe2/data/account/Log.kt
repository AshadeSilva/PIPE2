package org.example.pipe2.data.account

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class Log (private val auth: Authentication) {

    val accountTypeFlow: Flow<AccountType?> = auth.currentUser?.let { user ->
        Firebase.firestore
            .collection("users")
            .document(user.uid)
            .snapshots // This listens for real-time changes
            .map { snapshot ->
                val typeString: String? = snapshot.get("account_type")
                AccountType.entries.find { it.name.equals(typeString, ignoreCase = true) }
            }
    } ?: flowOf(null)

}