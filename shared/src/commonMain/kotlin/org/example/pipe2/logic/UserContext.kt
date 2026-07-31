package org.example.pipe2.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.pipe2.data.account.AccountDB
import org.example.pipe2.oldLogic.LocalAppContext
import org.example.pipe2.ui.generalLayout.Page
import org.example.pipe2.ui.theme.White
import org.example.pipe2.utils.logDebug

class UserContext(private val accountDB: AccountDB) : ViewModel() {

    // TODO: will eventually become local storage
    val savedUsers: MutableList<User> = mutableListOf()
    var currentUser by mutableStateOf<User?>(null)

    init {
        observeAccountChanges()
    }

    private fun observeAccountChanges() {
        val db = accountDB
        viewModelScope.launch {
            snapshotFlow { db.currentDetails }.collectLatest { details ->
                if (details != null) {
                    updateUser(details)
                } else {
                    currentUser = null
                }
            }
        }
    }

    fun updateUser(details: DBResult) {
        // see if we have a local version
        var user = savedUsers.find { it.uid == details.uid }

        if (user == null) {
            // else copy remote account locally
            user = when (details.type){
                "student" -> Student()
                "warden" -> Warden()
                else -> null
            }
            if (user != null) {
                savedUsers.add(user)
            }
        }
        if (user != null){
            user.updateDetails(details)
        }
        currentUser = user
    }
}