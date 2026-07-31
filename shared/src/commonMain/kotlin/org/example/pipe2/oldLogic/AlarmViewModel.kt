package org.example.pipe2.oldLogic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// is alarm activated, off or post-alarm
class AlarmViewModel: ViewModel() {
    var state by mutableStateOf<AlarmState>(DeactiveState())
    fun toggle(){
        state = state.nextState
    }
}