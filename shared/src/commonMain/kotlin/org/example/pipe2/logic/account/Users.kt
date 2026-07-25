package org.example.pipe2.logic.account

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Users(private val auth: Authentication) {

    val accountTypeFlow: Flow<AccountType?> = auth.currentUser?.let { user ->
        Firebase.firestore
            .collection("users")
            .document(user.uid)
            .snapshots // This listens for real-time changes
            .map { snapshot ->
                val typeString: String? = snapshot.get("account_type")
                AccountType.entries.find { it.name.equals(typeString, ignoreCase = true) }
            }
    } ?: kotlinx.coroutines.flow.flowOf(null)


    suspend fun getAccountType(): AccountType {
        val uid = auth.currentUser?.uid ?: return AccountType.none
        
        return try {val snapshot = Firebase.firestore
            .collection("users")
            .document(uid)
            .get()

            // Access the field by name. Using String? handles cases where the field might be missing.
            val typeString: String? = snapshot.get("account_type")

            for (type in AccountType.entries){
                if (type.name == typeString){
                    return type
                }
            }
            return AccountType.none

        } catch (e: Exception) {
            AccountType.none
        }
    }
}

enum class AccountType(name: String) {
    none("none"),
    student("student"),
    warden("warden")
}
