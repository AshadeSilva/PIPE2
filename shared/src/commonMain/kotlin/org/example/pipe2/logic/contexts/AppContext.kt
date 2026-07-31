package org.example.pipe2.logic.contexts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.pipe2.data.account.FirebaseAccountDB

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
    val accountDB = viewModel { FirebaseAccountDB() }
    val user = viewModel { UserContext(accountDB) }
    val log = viewModel { LogContext(user) }
    val ui = viewModel { UIContext(user) }
    val loginPage = viewModel { LogInPageContext(accountDB) }

    return remember(user, log, loginPage) { AppContext(user, log, ui, loginPage) }
}
