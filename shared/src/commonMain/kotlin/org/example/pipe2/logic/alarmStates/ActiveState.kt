package org.example.pipe2.logic.alarmStates

class ActiveState: AlarmState {
    override val wardenActivateButton: String
        get() = "End Alarm"
    override val isActive: Boolean
        get() = true
}