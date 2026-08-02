package org.example.pipe2.logic.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.example.pipe2.data.account.LocalAccountDB
import org.example.pipe2.data.account.UserDetails

abstract class User(uid: String, private val scope: CoroutineScope, private val db: LocalAccountDB) {
    val uid = uid
    var username: String by mutableStateOf("")
    var building: String by mutableStateOf("")
    var email: String by mutableStateOf("")

    private var job: Job? = null

    init {
        updateAccount()
    }

     fun updateAccount() {
        job?.cancel()
        job = scope.launch {
            db.getUserDocument(uid)?.collect {
                updateDetails(it)
            }
        }
    }

    fun updateDetails(details: UserDetails) {
        // TODO: if (uid != details.uid) throw some kind of error
        email = details.email ?: ""
        username = details.username ?: ""
        building = details.building ?: ""
    }
}

class Warden(uid: String, scope: CoroutineScope, db: LocalAccountDB): User(uid, scope, db)
class Student(uid: String, scope: CoroutineScope, db: LocalAccountDB): User(uid, scope, db)