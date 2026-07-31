package org.example.pipe2.logic

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.pipe2.data.log.FirestoreLog
import org.example.pipe2.oldLogic.Event
import org.example.pipe2.oldLogic.UIContext.AccountTheme
import org.example.pipe2.utils.logDebug

// container for the log to be in. Sometimes empty. Same object throughout
// type of Log class is chosen in createLog
class LogContext(user: UserContext) : ViewModel() {

    var title by mutableStateOf("No Log Open")
    private var log by mutableStateOf<Log?>(null)

    init {
        viewModelScope.launch {
            snapshotFlow { user.currentUser }.collectLatest {
                val newUser = user.currentUser
                if ( newUser != null) {
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
        if (user::class.simpleName == "Warden")
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
