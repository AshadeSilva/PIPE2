package org.example.pipe2.logic.alarmStates

interface AlarmState {
    val wardenActivateButton: String
    val isActive: Boolean
    val stateName: String
    val nextState: AlarmState
}