package org.example.pipe2.logic.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.example.pipe2.data.account.AccountDatabaseSyncer
import org.example.pipe2.data.account.AccountLogicSyncer
import org.example.pipe2.data.account.CorruptedAccountError
import org.example.pipe2.data.account.ForceSignOut
import org.example.pipe2.data.account.UserNotFoundError
import org.example.pipe2.data.auth.remote.RemoteAuth
import org.example.pipe2.utils.logDebug

class UserContext(
    private val auth: RemoteAuth,
    private val dbSync: AccountDatabaseSyncer,
    private val userSync: AccountLogicSyncer
) : ViewModel() {

    var currentUser by mutableStateOf<User?>(null)
        private set

    val currentUserFlow: Flow<User?> = snapshotFlow { currentUser }
    private var currentUserSession: UserSession? = null
        get() = field
        set(value) {
            field = value
        }

    // app lifetime
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
        viewModelScope.launch {
            signInLock.withLock {
                if (currentUserSession != null) return@launch // Already signed in via manual call

                val savedUID = dbSync.getUser()
                if (savedUID != null) {
                    val exceptionHandler = CoroutineExceptionHandler { _, _ -> signOut() }
                    val userSessionScope =
                        CoroutineScope(SupervisorJob() + Dispatchers.Default + exceptionHandler)

                    updateSession(UserSession(savedUID, dbSync, userSync, userSessionScope))
                }
            }
        }
    }

    private fun updateSession(newSession: UserSession?) {
        currentUserSession?.removeUser()
        currentUserSession = newSession
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

                if (signIn.uid != currentUserSession?.uid) {

                    dbSync.rememberUser(signIn.uid!!)
                    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                        viewModelScope.launch { signOut() }
                    }
                    val userSessionScope =
                        CoroutineScope(SupervisorJob() + Dispatchers.Default + exceptionHandler)

                    updateSession(
                        UserSession(
                            signIn.uid!!,
                            dbSync,
                            userSync,
                            userSessionScope
                        )
                    )
                    continueAuth(email, password, userSessionScope)
                }
            } catch (e: Exception) {
                when (e) {
//                    is CancellationException -> throw e
//                    is IncorrectPassword -> throw e
                    is ForceSignOut -> signOut()
                    is CorruptedAccountError -> {
                        viewModelScope.launch {
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
        currentUser = null
        signInLifetime?.scope?.cancel()
        signInLifetime = null
        updateSession(null)
        viewModelScope.launch {
            dbSync.forgetUser()
            auth.signOut()
        }
    }

    private inner class UserSession(
        val uid: String,
        private val dbSyncer: AccountDatabaseSyncer,
        private val accountLogicSyncer: AccountLogicSyncer,
        private val scope: CoroutineScope
    ) {

        init {
            currentUser = User(uid)
            logDebug("ASHADEBUG", "user switched $uid")
            signIn()
        }

        fun removeUser() {
            scope.cancel()
        }

        private fun signIn() {
            scope.launch {
                dbSyncer.start(uid)
            } // start remote
            scope.launch {
                accountLogicSyncer.observeUser(uid).collect { newUser ->
                    if (newUser != null) {
                        currentUser = newUser
                        logDebug("ASHADEBUG", "user: ${currentUser?.email}")
                    }
                }
            } // start local
        }

    }

}