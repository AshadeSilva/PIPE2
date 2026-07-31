package org.example.pipe2.oldLogic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.pipe2.data.account.FirebaseAccountDB
import org.example.pipe2.data.account.LogInProcessor
import org.example.pipe2.logic.LogContext
import org.example.pipe2.logic.UserContext

class AppContext(
    val loginContext: LogInProcessor,
    val alarm: AlarmViewModel,
    val user: UserContext,
    val log: LogContext
)

val LocalAppContext = staticCompositionLocalOf<AppContext> {
    error("No AppContext provided")
}

@Composable
fun rememberAppContext(): AppContext {
    val accountDB = FirebaseAccountDB.instance
    val loginContext = viewModel { LogInProcessor(accountDB) }
    val alarm = viewModel { AlarmViewModel() }
    val user = viewModel { UserContext(accountDB) }
    val log = viewModel { LogContext.instance }
    return remember(loginContext, alarm, user, log) { AppContext(loginContext, alarm, user, log) }
}
