package org.example.pipe2.logic.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.example.pipe2.data.account.DatabaseSyncer
import org.example.pipe2.data.account.UserSyncer
import org.example.pipe2.data.account.CorruptedAccountError
import org.example.pipe2.data.account.ForceSignOut
import org.example.pipe2.data.account.IncorrectPassword
import org.example.pipe2.data.account.UserNotFoundError
import org.example.pipe2.data.account.remote.RemoteAuth
import org.example.pipe2.utils.logDebug
import kotlin.math.sign

class UserContext(
    private val auth: RemoteAuth,
    private val dbSync: DatabaseSyncer,
    private val userSync: UserSyncer
) {

    var currentUser by mutableStateOf<User?>(null)
        private set
    private var currentUserSession: UserSession? = null
        get() = field
        set(value) {
            field = value
            logDebug("ASHADEBUG", "signed in ${value?.uid}")
        }

    // app lifetime
    val generalScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var signInLock = Mutex()

    // signInLifetime
    private var signInLifetime: SignInLifetime? = null
    private class SignInLifetime {

        val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
        var uid: String? = null // only exists in signIn lifetime
            private set

        fun setUid(value: String?) {
            uid = value
        }
    }


    init {
        // remember who was signed in before
        generalScope.launch {
            val savedUID = dbSync.getUser()
            if (savedUID!=null){
                val exceptionHandler = CoroutineExceptionHandler { _, _ -> signOut() }
                val userSessionScope = CoroutineScope(SupervisorJob() + Dispatchers.Default + exceptionHandler)
                currentUserSession = UserSession(savedUID, dbSync, userSync, userSessionScope)
            }
        }
    }

    suspend fun signIn(email: String, password: String) {
        signInLock.withLock {
            val signIn = SignInLifetime()
            signInLifetime = signIn

            // Link cancellation of signIn.scope to this coroutine
            val currentJob = kotlin.coroutines.coroutineContext[Job]!!
            val registration = signIn.scope.coroutineContext[Job]?.invokeOnCompletion { cause ->
                if (cause is CancellationException) {
                    currentJob.cancel(cause)
                }
            }

            try {
                authenticate(email, password)
                // uid should be non null, or exception
                logDebug("ASHADEBUG", "authenticated ${signIn.uid}")

                if (signIn.uid != currentUserSession?.uid) {

                    dbSync.rememberUser(signIn.uid!!)
                    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                        generalScope.launch { signOut() }
                    }
                    val userSessionScope = CoroutineScope(SupervisorJob() + Dispatchers.Default + exceptionHandler)

                    currentUserSession?.removeUser()
                    currentUserSession = UserSession(
                        signIn.uid!!,
                        dbSync,
                        userSync,
                        userSessionScope
                    )
                    logDebug("ASHADEBUG", "made user ")
                    continueAuth(email, password, userSessionScope)
                }
            } catch (e: Exception) {
                when (e) {
//                    is CancellationException -> throw e
//                    is IncorrectPassword -> throw e
                    is ForceSignOut -> signOut()
                    is CorruptedAccountError -> {
                        generalScope.launch {
                            signIn.uid?.let { dbSync.removeUser(it) }
                        }
                        signOut()
                    }
                    else -> throw e
                }
            } finally {
                registration?.dispose()
                signInLifetime = null
                signIn.scope.cancel()
            }
        }
    }


    suspend fun authenticate(email:String, password:String) {
        // local signIn (should succeed immediately unless new user)
        try {
            signInLifetime?.setUid(dbSync.localAuth(email, password)           ) // may throw incorrect password
        } catch (e: UserNotFoundError){
            // ignore and check remote
        }
        // sign into remote (for DBSyncer)
        // may be long if theres bad connection. Will be cut off by sign in scope
        signInLifetime?.setUid(auth.signIn(email, password)) // may throw incorrect password
    }

    // if authentication for remote didn't work during sign in (ie currently no access to remote db),
    // continue trying to sign in
    fun continueAuth(email: String, password: String, userScope: CoroutineScope) {
        userScope.launch {
            while (auth.uid==null) {
                auth.signIn(email, password)
                delay(1_000)
            }
        }
    }

    fun signOut() {
        signInLifetime?.scope?.cancel()
        signInLifetime = null
        currentUserSession?.removeUser() // cancels the user-life scope
        currentUserSession = null
        generalScope.launch {
            dbSync.forgetUser()
            auth.signOut()
        }
    }

    private inner class UserSession(
        val uid: String,
        private val dbSyncer: DatabaseSyncer,
        private val userSyncer: UserSyncer,
        private val scope: CoroutineScope
    ) {

        init {
            currentUser = User(uid)
            signIn()
        }

        fun removeUser() {
            scope.cancel()
        }

        private fun signIn() {
            scope.launch { dbSyncer.start(uid) } // start remote
            scope.launch {
                userSyncer.observeUser(uid).collect { newUser ->
                    if (newUser != null) {
                        currentUser = newUser
                    }
                }
            } // start local
        }

    }

}