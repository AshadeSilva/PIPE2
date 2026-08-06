package org.example.pipe2.data.account

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.auth.FirebaseAuthInvalidUserException
import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class FirebaseAccountDB: RemoteAccountDB, ViewModel() {
    private val auth = Firebase.auth

    private val _currentDetails = MutableStateFlow<UserDetails?>(null)
    override val currentDetails: StateFlow<UserDetails?> = _currentDetails.asStateFlow()

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
                        _currentDetails.value = null
                        return@collect
                    }
                    try {
                        _currentDetails.value = UserDetails(
                            uid = result.get("uid"),
                            username = result.get("username"),
                            building = result.get("building"),
                            email = result.get("email"),
                            type = result.get("account_type")
                        )
                    } catch (_: Exception) {
                        _currentDetails.value = null
                        // TODO: Handle corrupted account state
                    }
                }
        }
    }

    // attempt to sign in
    override suspend fun signIn(email: String, password: String): UserDetails {
        // Attempt to sign in
        try {
            val user = auth.signInWithEmailAndPassword(email, password).user
            if (user != null) {
                _currentDetails.value = null // Clear old details to avoid race conditions
                startObservingDetails(user.uid)
                // Wait for the first set of details to be loaded
                return currentDetails.filterNotNull().first()
            } else {
                throw InvalidCredentialsError(email)
            }
        } catch (_: FirebaseAuthInvalidUserException) {
            throw InvalidCredentialsError(email)
        } catch (_: FirebaseAuthInvalidCredentialsException) {
            throw InvalidCredentialsError(email)
        }
    }

    override suspend fun signOut() {
        detailsJob?.cancel()
        _currentDetails.value = null
        auth.signOut()
    }
}
