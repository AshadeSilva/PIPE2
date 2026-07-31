package org.example.pipe2.logic.alarm

sealed class AlarmState {
    abstract val wardenActivateButton: String
    abstract val stateName: String
    abstract val nextState: AlarmState
}

class ActiveState: AlarmState(){
    override val wardenActivateButton = "End Alarm"
    override val stateName = "ALARM ACTIVATED"
    override val nextState
        get() = PostAlarmState()
}
class DeactiveState: AlarmState() {
    override val wardenActivateButton = "Activate Alarm"
    override val stateName = "No Alarm Active"
    override val nextState
        get() = ActiveState()
}
class PostAlarmState: AlarmState() {
    override val wardenActivateButton = "Clear"
    override val stateName = "Post Alarm"
    override val nextState
        get() = DeactiveState()
}
