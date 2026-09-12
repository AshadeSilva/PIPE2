package org.example.pipe2.apps.warden

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.pipe2.data.account.AccountDatabaseSyncer
import org.example.pipe2.data.account.AccountLogicSyncer
import org.example.pipe2.data.account.remote.RemoteAuth
import org.example.pipe2.data.log.LogDatabaseSyncer
import org.example.pipe2.data.log.LogLogicSyncer
import org.example.pipe2.logic.AppLogicContext
import org.example.pipe2.logic.user.UserContext
import org.example.pipe2.logic.alarm.AlarmContext
import org.example.pipe2.logic.user.UserType

class WardenAppContext(
    override val user: UserContext,
    override val alarm: AlarmContext
): AppLogicContext {}

val WardenApp = staticCompositionLocalOf<WardenAppContext> {
    error("No AppContext provided")
}

@Composable
fun rememberWardenApp(
    dbSyncer: AccountDatabaseSyncer,
    accountLogicSyncer: AccountLogicSyncer,
    auth: RemoteAuth,
    logDbSyncer: LogDatabaseSyncer,
    logLogicSyncer: LogLogicSyncer
): WardenAppContext {
    val user = viewModel { UserContext(auth, dbSyncer, accountLogicSyncer, UserType.Warden) }
    val log = viewModel { AlarmContext(user, logDbSyncer, logLogicSyncer) }
    return remember(user, log) { WardenAppContext(user, log) }
}
