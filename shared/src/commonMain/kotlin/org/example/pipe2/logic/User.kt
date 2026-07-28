package org.example.pipe2.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.example.pipe2.data.account.Authentication
import org.example.pipe2.ui.generalLayout.Page
import org.example.pipe2.ui.theme.White

class User(private val auth: Authentication) : ViewModel() {
    var accountType by mutableStateOf(AccountType.None)
    var username by mutableStateOf("")
    var building by mutableStateOf("")
    var email by mutableStateOf("")
    
    // Tracks the user for which data is currently loaded
    var user by mutableStateOf<FirebaseUser?>(auth.currentUser)
    private val mutex = Mutex()

    init {
        viewModelScope.launch {
            snapshotFlow { auth.currentUser }.collectLatest {
                updateDetails()
            }
        }
    }

    suspend fun updateDetails() = mutex.withLock {
        val currentUser = auth.currentUser
        user = currentUser

        if (currentUser == null) {
            resetFields()
            return@withLock // equiv to finally{unlock}
        }

        try {
            val snapshot = Firebase.firestore
                .collection("users")
                .document(currentUser.uid)
                .get()

            // get() returns a snapshot. We check if the expected data is there.
            val typeString: String? = snapshot.get("account_type")
            
            if (typeString == null) {
                resetFields()
            } else {
                accountType = AccountType.entries.find { 
                    it.firestormName.equals(typeString, ignoreCase = true) 
                } ?: AccountType.None
                
                username = snapshot.get("username") ?: ""
                building = snapshot.get("building") ?: ""
                email = snapshot.get("email") ?: ""
            }
        } catch (e: Exception) {
            // Handle network errors or permission issues (e.g., if account is deleted)
            resetFields()
        }
    }

    private fun resetFields() {
        accountType = AccountType.None
        username = ""
        building = ""
        email = ""
    }

}

enum class AccountType(val firestormName: String, val colour: Color, val pages: List<Page>) {
    None( "none", White, listOf(Page.LogIn)),
    Student( "student", org.example.pipe2.ui.theme.Student, listOf(Page.SelfRegister, Page.LogIn)),
    Warden( "warden", org.example.pipe2.ui.theme.Warden, listOf(Page.Students, Page.LogIn, Page.LogView))
}
