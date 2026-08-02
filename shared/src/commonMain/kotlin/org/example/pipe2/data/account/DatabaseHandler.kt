package org.example.pipe2.data.account

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// ensures that (FOR ACCOUNTS INFORMATION) data flows from remote -> local
class DatabaseHandler(val remoteAccountDB: RemoteAccountDB, val localAccountDB: LocalAccountDB): ViewModel() { // currently only handles accounts
    
    // database handler runs when the remote and local database are signed in to the correct account
    private var job: Job? = null
    private var currentUid: String? = null

    // watch remote DB for changes to user account details and pass them to remote DB
    private fun observeAccountChanges() {
        job?.cancel()
        job = viewModelScope.launch {
            snapshotFlow { remoteAccountDB.currentDetails }.collectLatest { details ->
                if (details != null ) {
                    check(details.uid == currentUid)
                    localAccountDB.updateDetails(details)
                } else stop(true)
            }
        }
    }

    // once signed in, begin watching remote DB and updating local DB for account information
    fun start(uid: String) {
        currentUid = uid
        observeAccountChanges()
    }

    // once signed out, stop watching remote DB
    fun stop(withError: Boolean = false) {
        job?.cancel()
        if (withError){
            currentUid?.let { localAccountDB.removeUserDocument(it) }
        }
    }
}