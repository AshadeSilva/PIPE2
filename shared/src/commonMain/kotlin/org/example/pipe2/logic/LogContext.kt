package org.example.pipe2.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.pipe2.data.log.FirestoreLog
import org.example.pipe2.oldLogic.Event

// container for the log to be in. Sometimes empty. Same object throughout
// type of Log class is chosen in createLog
class LogContext private constructor(): ViewModel() {
    companion object {
        val instance = LogContext()
    }

    var title by mutableStateOf("No Log Open")
    private var log by mutableStateOf<Log?>(null)
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
