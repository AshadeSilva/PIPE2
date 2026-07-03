package org.example.pipe2.logic.account

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.semantics.password
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.launch


class Authentication: ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    fun signInOrSignUp() {

        if (email.isBlank() || password.isBlank()) return

        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                // Attempt to sign in
                val result = try {
                    auth.signInWithEmailAndPassword(email, password)
                } catch (e: Exception) {
                    // If sign in fails, try to sign up (create user)
                    auth.createUserWithEmailAndPassword(email, password)
                }

                val user = result.user
                if (user != null) {
                    // Check and create Firestore entry if it doesn't exist
                    val userDoc = firestore.collection("users").document(user.uid)
                    if (!userDoc.get().exists) {
                        userDoc.set(mapOf(
                            "email" to email,
                            "uid" to user.uid,
                            "createdAt" to dev.gitlive.firebase.firestore.FieldValue.serverTimestamp
                        ))
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Authentication failed"
            } finally {
                isLoading = false
            }
        }
    }
}