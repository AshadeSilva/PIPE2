package org.example.pipe2.ui.students

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import org.example.pipe2.logic.alarmStates.ActiveState
import org.example.pipe2.logic.alarmStates.AlarmState
import org.example.pipe2.logic.alarmStates.DeactiveState
import org.example.pipe2.utils.logDebug


class AlarmViewModel: ViewModel() {
    var state by mutableStateOf<AlarmState>(DeactiveState())

    fun isActive() = state.isActive

    fun toggle() {
        logDebug("ASHADEBUG", "Toggle button clicked")
        if (isActive()) {
            state = DeactiveState()
        } else {
            state = ActiveState()
        }

        logDebug("ASHADEBUG", "State toggled! New isActive = ${isActive()}")
    }

    fun wardenActivateButtonText(): String {
        return state.wardenActivateButton
    }

}
