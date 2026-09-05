package org.example.pipe2.logic.alarm

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.pipe2.data.alarm.FirestoreLog
import org.example.pipe2.data.alarm.Log
import org.example.pipe2.logic.user.User
import org.example.pipe2.logic.user.UserContext
import org.example.pipe2.logic.user.UserType

// container for the log to be in. Sometimes empty. Same object throughout
// type of Log class is chosen in createLog
class AlarmContext(user: UserContext) : ViewModel() {

    var title by mutableStateOf("No Log Open")
    private var log by mutableStateOf<Log?>(null)

    init {
        observeUserChanges(user)
    }

    private fun observeUserChanges(userContext: UserContext) {
        viewModelScope.launch {
            snapshotFlow { userContext.currentUser }.collectLatest { newUser ->
                if (newUser != null) {
                    updateLogStatus(newUser)
                }
            }
        }
    }

    val events: List<Event>
        get() = log?.events ?: listOf()
    val error: Exception?
        get() = log?.lastError

    fun createLog(alarmId: String) {
        // note: using viewModelScope for cleaning up CoRoutine for firestore listening
        log = FirestoreLog(alarmId, viewModelScope)
        title = "Log: $alarmId"
    }

    fun updateLogStatus(user: User) {
        if (user.type == UserType.Warden)
        {
            createLog("example_alarm")
        } else {
            closeLog()
        }
    }

    fun closeLog() {
        log = null
        title = "No Log Open"
    }

    fun crash() {
        log?.triggerCrash()
    }
}