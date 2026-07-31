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

    CompositionLocalProvider(LocalAppContext provides appContext) {
        AppTheme {
            GeneralPage()
        }
    }
}
