package org.example.pipe2.logic.account

import androidx.compose.ui.graphics.Color
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.pipe2.ui.theme.Student
import org.example.pipe2.ui.theme.Warden
import org.example.pipe2.ui.theme.White

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
        val uid = auth.currentUser?.uid ?: return AccountType.None
        
        return try {val snapshot = Firebase.firestore
            .collection("users")
            .document(uid)
            .get()

            // Access the field by name. Using String? handles cases where the field might be missing.
            val typeString: String? = snapshot.get("account_type")

            for (type in AccountType.entries){
                if (type.firestormName == typeString){
                    return type
                }
            }
            return AccountType.None

        } catch (e: Exception) {
            AccountType.None
        }
    }
}

enum class AccountType(val infoBar: String, val firestormName: String, val colour: Color) {
    None("none", "none", White),
    Student("STUDENT", "student", org.example.pipe2.ui.theme.Student),
    Warden("WARDEN", "warden", org.example.pipe2.ui.theme.Warden)
}
