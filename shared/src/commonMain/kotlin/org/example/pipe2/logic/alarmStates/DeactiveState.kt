package org.example.pipe2.logic.alarmStates

class DeactiveState: AlarmState {
    override val wardenActivateButton: String
        get() = "Activate Alarm"
    override val isActive: Boolean
        get() = false
}