package org.example.pipe2.logic

import androidx.compose.runtime.staticCompositionLocalOf
import org.example.pipe2.logic.user.UserContext
import org.example.pipe2.logic.alarm.AlarmContext

interface AppLogicContext{
    val user: UserContext
    val alarm: AlarmContext
}

val SharedApp = staticCompositionLocalOf<AppLogicContext> {
    error("No AppContext provided")
}