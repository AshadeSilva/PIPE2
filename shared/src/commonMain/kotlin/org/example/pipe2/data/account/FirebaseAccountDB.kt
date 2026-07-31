package org.example.pipe2.data.account

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.FieldValue
import org.example.pipe2.logic.DBResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.collectLatest
import org.example.pipe2.utils.logDebug


class FirebaseAccountDB private constructor(): AccountDB {
    private val auth = Firebase.auth
    override var currentDetails by mutableStateOf<DBResult?>(null)
        private set

    companion object {
        val instance = FirebaseAccountDB()
    }

    override suspend fun listenUser() = auth.authStateChanged.collectLatest {
        if (it == null) {
            currentDetails = null
        }
        else {
            changeUser(it)
        }
    }

    private suspend fun changeUser(user: FirebaseUser) {
        try {
            Firebase.firestore
                .collection("users")
                .document(user.uid)
                .snapshots
                .collect { result ->
                    val uid: String? = result.get("uid")
                    if (uid == null) {
                        currentDetails = null
                    } else {
                        currentDetails = DBResult(
                            uid,
                            result.get("username"),
                            result.get("building"),
                            result.get("email"),
                            result.get("type")
                        )
                    }
                }
        } catch (e: Exception) {
            currentDetails = null
        }
    }

    override suspend fun signInSignUp(email: String, password: String) {
        logDebug("ASHADEBUG", "signing/up in as $email")
        // Attempt to sign in
        val result = try {
            val r = auth.signInWithEmailAndPassword(email, password)
            logDebug("ASHADEBUG", "signing in as $email")
            r
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
                    "type" to "student",
                    "createdAt" to FieldValue.serverTimestamp
                )
            )
        }
    }

    override suspend fun signOut() = auth.signOut()
}