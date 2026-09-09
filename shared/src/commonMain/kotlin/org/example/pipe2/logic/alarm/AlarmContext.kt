package org.example.pipe2.logic.alarm

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.pipe2.data.log.LogDatabaseSyncer
import org.example.pipe2.data.log.LogLogicSyncer
import org.example.pipe2.logic.user.UserContext
import org.example.pipe2.logic.user.UserType

// container for the log to be in. Sometimes empty. Same object throughout
// type of Log class is chosen in createLog
class AlarmContext(private val userContext: UserContext, val dbSyncer: LogDatabaseSyncer, val logicSyncer: LogLogicSyncer): ViewModel() {

    var title by mutableStateOf("No Log Open")
    var events = mutableStateListOf<Event>()
    var error by mutableStateOf<Throwable?>(null)
    private var watchJob: Job? = null

    init {
        // current crude version only has one alarm, so just watch user
        observeUserChanges()
    }

    private var watchingAlarmId: String? = null

    private fun startWatching(alarmId: String) {
        if (watchingAlarmId == alarmId) return
        watchingAlarmId = alarmId

        watchJob?.cancel()
        error = null
        watchJob = viewModelScope.launch {
            launch {
                try {
                    dbSyncer.start(alarmId)
                } catch (e: Exception) {
                    error = e
                }
            }
            launch { logicSyncer.loadLog(events) }
            title = "Alarm log: " + alarmId
        }
    }

    fun crash() {
        error?.let { throw it }
    }

    // if theres a new user, clear
    private fun observeUserChanges() {
        viewModelScope.launch {
            userContext.currentUserFlow.collectLatest { newUser ->
                if (newUser?.type == UserType.Warden) {
                    startWatching("example_alarm")
                } else {
                    watchJob?.cancel()
                    watchJob = null
                    watchingAlarmId = null
                    events.clear()
                    title = "No Log Open"
                }
            }
        }

    }


}