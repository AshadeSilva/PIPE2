package org.example.pipe2.logic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.pipe2.data.account.FirebaseAccountDB
import org.example.pipe2.data.account.DatabaseHandler
import org.example.pipe2.data.account.DummyLocalAccountDB
import org.example.pipe2.logic.account.LogInPageContext
import org.example.pipe2.logic.account.SessionManager
import org.example.pipe2.logic.account.UserContext
import org.example.pipe2.logic.alarm.LogContext

class AppContext(
    val user: UserContext,
    val log: LogContext,
    val ui: UIContext,

    // remember state for pages
    val loginPage: LogInPageContext
)

val LocalAppContext = staticCompositionLocalOf<AppContext> {
    error("No AppContext provided")
}

@Composable
fun rememberAppContext(): AppContext {
    val remoteAccountDB = viewModel { FirebaseAccountDB() }
    val localAccountDB = DummyLocalAccountDB()
    val databaseHandler = viewModel { DatabaseHandler(remoteAccountDB, localAccountDB) }

    val user = viewModel { UserContext(localAccountDB) }

    val sessionManager = SessionManager(remoteAccountDB, localAccountDB, databaseHandler, user)
    val log = viewModel { LogContext(user) }
    val ui = viewModel { UIContext(user) }
    val loginPage = viewModel { LogInPageContext(sessionManager) }

    return remember(user, log, loginPage) { AppContext(user, log, ui, loginPage) }
}
