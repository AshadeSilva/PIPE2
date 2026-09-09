package org.example.pipe2.data.account

import dev.gitlive.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.example.pipe2.data.account.local.LocalAccountDB
import org.example.pipe2.data.account.remote.RemoteAccountDB
import org.example.pipe2.data.auth.local.LocalAuthDB
import org.example.pipe2.data.auth.remote.RemoteAuth
import org.example.pipe2.utils.logDebug

// ensures that (FOR ACCOUNTS INFORMATION) data flows from remote -> local
// any account details fetched from remote are updated in local
class AccountDatabaseSyncer(
    private val remote: RemoteAccountDB,
    private val local: LocalAccountDB,
    private val localAuth: LocalAuthDB,
    private val auth: RemoteAuth
) {
    // store users
    suspend fun start(uid: String) {
        var active = true
        while (active) {
            active = false
            try {
                if (auth.uid!=uid){
                    throw NotAuthenticated()
                }
                observe(uid)
            }  catch (_: NotAuthenticated) {
                delay(1_000)
                active = true
            } catch (_: FirebaseFirestoreException) {
                // TODO: read documentation. Meant to respond to bad connection
                delay(1_000)
                active = true
            }

            catch (e: CorruptedAccountError) {
                throw e
            }
        }

    }

    private suspend fun observe(uid: String) = remote.observe(uid).collectLatest { details ->
        if (details==null){
            throw UserNotRemote()
        } else if (details.uid != uid) {
            throw CorruptedAccountError(uid)
        } else {
            local.updateDetails(details)
        }
    }
    suspend fun removeUser(uid: String) {
        local.removeUser(uid)
    }

    suspend fun localAuth(email: String, password: String): String = local.getAccount(email, password)

    // remember previous user
    suspend fun rememberUser(uid: String) = localAuth.rememberUser(uid)
    suspend fun forgetUser() = localAuth.forgetUser()
    suspend fun getUser(): String? = localAuth.getUser()


}