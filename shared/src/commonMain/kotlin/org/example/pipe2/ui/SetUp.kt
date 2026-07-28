package org.example.pipe2.ui

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import org.example.pipe2.data.account.LogModel
import org.example.pipe2.ui.theme.AppTheme
import org.example.pipe2.logic.LocalAppContext
import org.example.pipe2.logic.LogUpdater
import org.example.pipe2.logic.rememberAppContext
import org.example.pipe2.ui.generalLayout.GeneralPage

@Composable
@Preview
fun SetUp() {
    val log = remember { LogUpdater(LogModel("example_alarm")) }
    val appContext = rememberAppContext(log)
    LaunchedEffect(Unit) {
        log.updateLog()
    }

    CompositionLocalProvider(LocalAppContext provides appContext) {
        AppTheme {
            GeneralPage()
        }
    }
}

