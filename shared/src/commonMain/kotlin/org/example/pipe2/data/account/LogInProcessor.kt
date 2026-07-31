package org.example.pipe2.data.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import org.example.pipe2.utils.logDebug


class LogInProcessor(val accountDB: AccountDB): ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun signInOrSignUp() {
        logDebug("ASHADEBUG", "signing in as $email")
        if (email.isBlank() || password.isBlank()) return
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