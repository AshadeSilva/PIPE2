package org.example.pipe2.logic.contexts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.pipe2.data.account.AccountDB
import org.example.pipe2.utils.logDebug

class LogInPageContext(val accountDB: AccountDB): ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun signInOrSignUp() {
        logDebug("ASHADEBUG", "LogInPageContext: signing in as $email")
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Please enter both email and password"
            return
        }
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                accountDB.signInSignUp(email, password)
            } catch (e: Exception) {
                errorMessage = e.message ?: "Authentication failed"
            } finally {
                isLoading = false
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            accountDB.signOut()
        }
    }
}