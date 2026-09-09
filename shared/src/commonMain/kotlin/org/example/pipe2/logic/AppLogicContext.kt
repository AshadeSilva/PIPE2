package org.example.pipe2.logic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.pipe2.data.account.AccountDatabaseSyncer
import org.example.pipe2.data.account.AccountLogicSyncer
import org.example.pipe2.data.auth.remote.RemoteAuth
import org.example.pipe2.data.log.LogDatabaseSyncer
import org.example.pipe2.data.log.LogLogicSyncer
import org.example.pipe2.logic.user.UserContext
import org.example.pipe2.logic.alarm.AlarmContext

class AppLogicContext(
    val user: UserContext,
    val alarm: AlarmContext
    )

val LocalAppLogicContext = staticCompositionLocalOf<AppLogicContext> {
    error("No AppContext provided")
}

@Composable
fun rememberAppContext(
    dbSyncer: AccountDatabaseSyncer,
    accountLogicSyncer: AccountLogicSyncer,
    auth: RemoteAuth,
    logDbSyncer: LogDatabaseSyncer,
    logLogicSyncer: LogLogicSyncer
): AppLogicContext {
    val user = viewModel { UserContext(auth, dbSyncer, accountLogicSyncer) }
    val log = viewModel { AlarmContext(user, logDbSyncer, logLogicSyncer) }
    return remember(user, log) { AppLogicContext(user, log) }
}
