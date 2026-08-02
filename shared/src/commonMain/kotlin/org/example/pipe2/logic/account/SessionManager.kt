package org.example.pipe2.logic.account

import kotlinx.coroutines.delay
import org.example.pipe2.data.account.DatabaseHandler
import org.example.pipe2.data.account.InvalidCredentialsError
import org.example.pipe2.data.account.LocalAccountDB
import org.example.pipe2.data.account.RemoteAccountDB
import org.example.pipe2.data.account.UserNotFoundError


// handles signing into the app, the remote database and the local database
class SessionManager (val remote: RemoteAccountDB,
                      val local: LocalAccountDB,
                      val databaseHandler: DatabaseHandler,
                      val userContext: UserContext
) {

     /* disable Database Handler
     sign in user to local DB
     if remoteUser != currentUser, continuously attempt to sign in the user
     if it fails from credentials, give an error, if for connectivity keep trying */
    suspend fun signIn(email: String, password: String) {
         databaseHandler.stop()
         try {
             // user is in local DB. Sign in and then link to remote
             val uid = local.authenticate(email, password)
             userContext.switchUser(uid)
             signInRemote(email, password)

         } catch (_: UserNotFoundError) {
             // user not in local DB. linking to remote, add user to localDB, then sign in
             // TODO: show on ui "looking for account"
             signInRemote(email, password) // this should load a new account into local
             val uid = local.authenticate(email, password)
             userContext.switchUser(uid)

         } catch (e: InvalidCredentialsError) {
             // TODO: show on ui "incorrect password"
             failSignIn()
         }

    }

    suspend fun signInRemote(email: String, password: String) {
        while (true) {
            try {
                remote.signIn(email, password)
                val uid = remote.currentDetails?.uid ?: throw InvalidCredentialsError(email)
                databaseHandler.start(uid)

                // Wait for local DB to catch up before returning
                while (local.getUserDocument(uid) == null) {
                    delay(50)
                }

                break
            } catch (e: InvalidCredentialsError) {
                failSignIn(e)

            } catch (e: Exception) {
                //TODO: change to something better.
                // Important: if fail to connect, keep trying. May need a device-specific connectivity monitor
                delay(5000) // Retry in 5 seconds.
            }
        }
    }

    suspend fun failSignIn(e: Exception? = null) {
        signOut()
        if (e != null){
            throw e
        }
    }

    // disable database handler
    // sign out user from local DB
    // continuously attempt to sign out remoteDB from current user
    suspend fun signOut() {
        databaseHandler.stop()
        userContext.currentUser = null
        remote.signOut()
    }
}