package org.example.pipe2.logic.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.example.pipe2.data.account.local.LocalAccountDB
import org.example.pipe2.data.account.UserDetails
import org.example.pipe2.logic.LocalAppContext

abstract class User(uid: String, private val scope: CoroutineScope, private val db: LocalAccountDB) {
    val uid = uid
    var username: String by mutableStateOf("")
    var building: String by mutableStateOf("")
    var email: String by mutableStateOf("")
    abstract val type: String

    private var job: Job? = null

    init {
        updateAccount()
    }

     fun updateAccount() {
        job?.cancel()
        job = scope.launch {
            db.getUserDocument(uid).collect {
                if (it == null) {
                    deleteUser()
                } else if (it.uid != uid) {
                    deleteUser()
                } else if (it.type!=type){
                    deleteUser()
                } else {
                    updateDetails(it)
                }

            }
        }
    }

    fun updateDetails(details: UserDetails) {
        email = details.email ?: ""
        username = details.username ?: ""
        building = details.building ?: ""
    }

    fun deleteUser() {
        // TODO("force sign out across app if not found/ corrupted in local database ")
        // use one of the account errors?
    }

}

class Warden(uid: String, scope: CoroutineScope, db: LocalAccountDB): User(uid, scope, db) {
    override val type = "warden"

}
class Student(uid: String, scope: CoroutineScope, db: LocalAccountDB): User(uid, scope, db) {
    override val type = "student"
}