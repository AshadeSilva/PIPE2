package org.example.pipe2.ui

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import org.example.pipe2.ui.theme.AppTheme
import org.example.pipe2.logic.LocalAppContext
import org.example.pipe2.logic.rememberAppContext
import org.example.pipe2.ui.generalLayout.GeneralPage

@Composable
@Preview
fun SetUp() {
    val appContext = rememberAppContext()

    // Relaunch log when user or their account type changes (account is loading immediately after user)
    LaunchedEffect(appContext.user.user, appContext.user.accountType) {
        appContext.log.updateLogStatus(appContext.user.accountType)
    }

    CompositionLocalProvider(LocalAppContext provides appContext) {
        AppTheme {
            GeneralPage()
        }
    }
}
