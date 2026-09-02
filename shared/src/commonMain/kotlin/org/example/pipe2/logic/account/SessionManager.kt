package org.example.pipe2.logic.account

import kotlinx.coroutines.delay
import org.example.pipe2.data.account.DatabaseHandler
import org.example.pipe2.data.account.local.InvalidCredentialsError
import org.example.pipe2.data.account.local.LocalAccountDB
import org.example.pipe2.data.account.remote.RemoteAccountDB
import org.example.pipe2.data.account.local.UserNotFoundError

/**
 * Coordinates the sign-in/sign-out process across local and remote databases.
 * Acts as the entry point for authentication flows.
 */
class SessionManager(
    private val remote: RemoteAccountDB,
    private val local: LocalAccountDB,
    private val databaseHandler: DatabaseHandler,
    private val userContext: UserContext
) {

    /**
     * Signs in the user by first checking local cache and then authenticating with the remote server.
     */
    suspend fun signIn(email: String, password: String) {
        try {
            // 1. Try local authentication for immediate UI response (Offline support)
            try {
                val localUid = local.authenticate(email, password)
                userContext.switchUser(localUid)
            } catch (e: UserNotFoundError) {}

            // 2. Sign in to firebase (starts syncing)
            val remoteUid = signInRemote(email, password)

            // 3. update UI
            userContext.switchUser(remoteUid)

        } catch (e: InvalidCredentialsError) {
            failSignIn(e)
        } catch (e: Exception) {
            // Other errors (e.g. network) are handled by retries in signInRemote or reported to UI
            throw e
        }
    }

    /**
     * Attempts to sign in to the remote database. Returns the UID on success.
     * Retries indefinitely on transient errors (like network).
     */
    private suspend fun signInRemote(email: String, password: String): String {
        while (true) {
            try {
                val details = remote.signIn(email, password)
                val uid = details.uid

                // used to wait for local to sync up - still needed?
//                while (local.getUserDocument(uid) == null) {
//                    delay(50)
//                }
                return uid
            } catch (e: InvalidCredentialsError) {
                throw e
            } catch (e: Exception) {
                // Connection error or similar, wait and retry
                delay(5000)
            }
        }
    }

    private suspend fun failSignIn(e: Exception) {
        val currentUid = userContext.currentUser?.uid
        signOut()
        if (currentUid != null) {
            databaseHandler.purgeUser(currentUid)
        }
        throw e
    }

    suspend fun signOut() {
        remote.signOut()
        userContext.signOut()
    }
}
