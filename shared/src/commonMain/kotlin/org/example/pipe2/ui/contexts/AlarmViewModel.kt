package org.example.pipe2.ui.contexts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.example.pipe2.logic.alarmStates.ActiveState
import org.example.pipe2.logic.alarmStates.AlarmState
import org.example.pipe2.logic.alarmStates.DeactiveState
import org.example.pipe2.utils.logDebug
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// is alarm activated, off or post-alarm
class AlarmViewModel: ViewModel() {
    var state by mutableStateOf<AlarmState>(DeactiveState())
    fun toggle() {
        state = state.nextState
        logDebug("ASHADEBUG", "State toggled! New state = ${state.stateName}")
    }
    fun wardenActivateButtonText(): String {
        return state.wardenActivateButton
    }
    fun stateName(): String {
        return state.stateName
    }
}