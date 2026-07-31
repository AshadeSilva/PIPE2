package org.example.pipe2.data.account

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.FieldValue
import org.example.pipe2.data.account.DBResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException


class FirebaseAccountDB: AccountDB, ViewModel() {
    private val auth = Firebase.auth
    override var currentDetails by mutableStateOf<DBResult?>(null)
        private set

    init {
        viewModelScope.launch {
            auth.authStateChanged.collectLatest {
                if (it == null) {
                    currentDetails = null
                }
                else {
                    changeUser(it)
                }
            }
        }
    }


    private suspend fun changeUser(user: FirebaseUser) {
        try {
            Firebase.firestore
                .collection("users")
                .document(user.uid)
                .snapshots
                .collect { result ->

                    if (!result.exists) {
                        currentDetails = null
                        return@collect
                    }

                    try {
                        currentDetails = DBResult(
                            uid = result.get("uid"),
                            username = result.get("username"),
                            building = result.get("building"),
                            email = result.get("email"),
                            type = result.get("account_type")
                        )
                    } catch (e: Exception) {
                        currentDetails = null
                    }
                }
        } catch (e: Exception) {
            if (e is CancellationException) throw e // Ensure cancellation works for collectLatest
            currentDetails = null
        }

    }

    override suspend fun signInSignUp(email: String, password: String) {
        // Attempt to sign in
        val result = try {
            auth.signInWithEmailAndPassword(email, password)
        } catch (e: Exception) {
            // If sign in fails, try to sign up (create user)
            auth.createUserWithEmailAndPassword(email, password)
        }

        val user = result.user
        if (user != null) {
            linkAccount(user, email)
        }

    }

    private suspend fun linkAccount(user: FirebaseUser, email: String){
        val userDoc = Firebase.firestore.collection("users").document(user.uid)
        val snapshot = try { userDoc.get() } catch (e: Exception) { null }

        if (snapshot == null || !snapshot.exists) {
            userDoc.set(
                mapOf(
                    "email" to email,
                    "uid" to user.uid,
                    "account_type" to "student",
                    "createdAt" to FieldValue.serverTimestamp
                )
            )
        }
    }

    override suspend fun signOut() = auth.signOut()
}
