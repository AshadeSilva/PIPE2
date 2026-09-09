package org.example.pipe2.data.auth.remote

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuthException
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import org.example.pipe2.data.account.IncorrectPassword
import org.example.pipe2.data.account.RemoteAuthFailed

class FirebaseAuth: RemoteAuth {

    private val auth = Firebase.auth
    override val uid
        get() = auth.currentUser?.uid

    // attempt to sign in
    override suspend fun signIn(email: String, password: String): String {
        while (true){
            try {
                val user = auth.signInWithEmailAndPassword(email, password).user
                return user?.uid ?: throw RemoteAuthFailed()
            } catch (e: FirebaseAuthException) {
                // wrong email password
                throw IncorrectPassword()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                delay(1_000)
            }
        }
    }

    override suspend fun signOut() = auth.signOut()
}