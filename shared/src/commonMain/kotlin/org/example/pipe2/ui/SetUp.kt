package org.example.pipe2.ui

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import org.example.pipe2.ui.theme.AppTheme
import org.example.pipe2.oldLogic.LocalAppContext
import org.example.pipe2.oldLogic.rememberAppContext
import org.example.pipe2.ui.generalLayout.GeneralPage

@Composable
@Preview
fun SetUp() {
    val appContext = rememberAppContext()

    // Relaunch log when user or their account type changes (account is loading immediately after user)
    LaunchedEffect(appContext.user.currentUser) {
        val user = appContext.user.currentUser
        if ( user != null) {
            appContext.log.updateLogStatus(user)
        }
    }

    CompositionLocalProvider(LocalAppContext provides appContext) {
        AppTheme {
            GeneralPage()
        }
    }
}
