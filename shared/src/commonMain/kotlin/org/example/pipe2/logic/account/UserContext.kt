package org.example.pipe2.logic.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.example.pipe2.data.account.local.LocalAccountDB

class UserContext(private val localDB: LocalAccountDB) : ViewModel() {

    var currentUser by mutableStateOf<User?>(null)
        private set

    fun switchUser(uid: String){
        if (currentUser?.uid == uid) return

        viewModelScope.launch {
            val type = localDB.getUserDocument(uid).firstOrNull()?.type

            currentUser = when (type){
                "student" -> Student(uid, viewModelScope, localDB)
                "warden" -> Warden(uid, viewModelScope, localDB)
                else -> null
            }
        }
    }

    fun signOut() {
        currentUser = null
    }

}