package org.example.pipe2.logic.alarmStates

class PostAlarmState: AlarmState {
    override val wardenActivateButton: String
        get() = "Clear"
    override val nextState: AlarmState
        get() = DeactiveState()
    override val isActive: Boolean
        get() = true
    override val stateName: String
        get() = "Post Alarm"
}