package org.example.pipe2.logic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.pipe2.data.account.Authentication

class AppContext(
    val auth: Authentication,
    val alarm: AlarmViewModel,
    val user: User,
    val log: LogView
)

val LocalAppContext = staticCompositionLocalOf<AppContext> {
    error("No AppContext provided")
}

@Composable
fun rememberAppContext(): AppContext {
    val auth = viewModel { Authentication() }
    val alarm = viewModel { AlarmViewModel() }
    val user = viewModel { User(auth) }
    val log = viewModel { LogView() }
    return remember(auth, alarm, user) { AppContext(auth, alarm, user, log) }
}
