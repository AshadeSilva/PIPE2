package org.example.pipe2.data.account

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.auth.FirebaseAuthInvalidUserException
import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class FirebaseAccountDB: RemoteAccountDB, ViewModel() {
    private val auth = Firebase.auth

    override var currentDetails by mutableStateOf<UserDetails?>(null)
        private set
    private var detailsJob: Job? = null


    // once signed in, watch account document for changes
    private fun startObservingDetails(uid: String){
        detailsJob?.cancel()
        detailsJob = viewModelScope.launch {
            Firebase.firestore
                .collection("users")
                .document(uid)
                .snapshots
                .collect { result ->
                    if (!result.exists) {
                        currentDetails = null
                        return@collect
                    }
                    try {
                        currentDetails = UserDetails(
                            uid = result.get("uid"),
                            username = result.get("username"),
                            building = result.get("building"),
                            email = result.get("email"),
                            type = result.get("account_type")
                        )
                    } catch (_: Exception) {
                        currentDetails = null
                        throw CorruptedAccountError(uid)
                    }
                }
        }
    }

    // attempt to sign in
    override suspend fun signIn(email: String, password: String) {
        // Attempt to sign in
        try {
            val user = auth.signInWithEmailAndPassword(email, password).user
            if (user != null) {
                startObservingDetails(user.uid)
                // Wait for the first set of details to be loaded
                snapshotFlow { currentDetails }.filterNotNull().first()
            }
        } catch (e: FirebaseAuthInvalidUserException) {
            throw InvalidCredentialsError(email)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw InvalidCredentialsError(email)
        }
    }

    override suspend fun signOut() {
        detailsJob?.cancel()
        currentDetails = null
        auth.signOut()
    }
}
