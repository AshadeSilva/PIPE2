package org.example.pipe2.data.account

import dev.gitlive.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.example.pipe2.data.account.local.LocalAccountDB
import org.example.pipe2.data.account.remote.RemoteAccountDB
import org.example.pipe2.data.account.remote.RemoteAuth
import org.example.pipe2.utils.logDebug

// ensures that (FOR ACCOUNTS INFORMATION) data flows from remote -> local
// any account details fetched from remote are updated in local
class DatabaseSyncer(private val remote: RemoteAccountDB, private val local: LocalAccountDB,
    // TODO: should be global
    private val auth: RemoteAuth
) {

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
            logDebug("ASHADEBUG", "remote updating details with ${details.email}")

        }
    }

    suspend fun localAuth(email: String, password: String): String = local.getAccount(email, password)

    suspend fun rememberUser(uid: String) = local.rememberUser(uid)
    suspend fun forgetUser() = local.forgetUser()
    suspend fun getUser(): String? = local.getUser()

    suspend fun removeUser(uid: String) {
        local.removeUser(uid)
    }
}