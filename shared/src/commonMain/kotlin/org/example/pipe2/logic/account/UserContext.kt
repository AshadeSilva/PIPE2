package org.example.pipe2.logic.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.pipe2.data.account.LocalAccountDB

class UserContext(private val localDB: LocalAccountDB) : ViewModel() {

    var currentUser by mutableStateOf<User?>(null)

    fun switchUser(uid: String){
        if (currentUser?.uid == uid) return

        val type = localDB.getUserDocument(uid)?.value?.type
        currentUser = when (type){
            "student" -> Student(uid, viewModelScope, localDB)
            "warden" -> Warden(uid, viewModelScope, localDB)
            else -> null
        }
    }

}