package org.example.pipe2.data.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.pipe2.data.account.local.LocalAccountDB
import org.example.pipe2.data.account.remote.RemoteAccountDB
import org.example.pipe2.utils.logDebug

// ensures that (FOR ACCOUNTS INFORMATION) data flows from remote -> local
// any account details fetched from remote are updated in local
class DatabaseHandler(val remoteAccountDB: RemoteAccountDB, val localAccountDB: LocalAccountDB): ViewModel() {

    init {
        observeAccountChanges()
    }

    private fun observeAccountChanges() {
        viewModelScope.launch {
            remoteAccountDB.currentDetails.collectLatest { details ->
                if (details != null) {
                    logDebug("DatabaseHandler", "Syncing remote details to local for UID: ${details.uid}")
                    localAccountDB.updateDetails(details)
                } else {
                    logDebug("DatabaseHandler", "Remote user signed out, stopping sync.")
                }
            }
        }
    }

    // remove user from local if corrupted / actively removed
    fun purgeUser(uid: String) {
        viewModelScope.launch {
            localAccountDB.removeUserDocument(uid)
        }
    }
}