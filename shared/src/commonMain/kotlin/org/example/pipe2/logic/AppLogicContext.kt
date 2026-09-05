package org.example.pipe2.logic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.pipe2.data.account.DatabaseSyncer
import org.example.pipe2.data.account.UserSyncer
import org.example.pipe2.data.account.remote.RemoteAuth
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
fun rememberAppContext(dbSyncer: DatabaseSyncer, userSyncer: UserSyncer, auth: RemoteAuth): AppLogicContext {
    val user = UserContext(auth, dbSyncer, userSyncer)
    val log = viewModel { AlarmContext(user) }
    return remember(user, log) { AppLogicContext(user, log) }
}
