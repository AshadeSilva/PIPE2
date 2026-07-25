package org.example.pipe2.ui.contexts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.pipe2.logic.account.Authentication
import org.example.pipe2.logic.account.Users

class AppContext(
    val auth: Authentication,
    val alarm: AlarmViewModel,
    val user: Users
)

val LocalAppContext = staticCompositionLocalOf<AppContext> {
    error("No AppContext provided")
}

@Composable
fun rememberAppContext(): AppContext {
    val auth = viewModel { Authentication() }
    val alarm = viewModel { AlarmViewModel() }
    val user = Users(auth)
    return AppContext(auth, alarm, user)
}
