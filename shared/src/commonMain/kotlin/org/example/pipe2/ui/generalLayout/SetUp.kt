package org.example.pipe2.ui.generalLayout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import org.example.pipe2.logic.contexts.LocalAppContext
import org.example.pipe2.logic.contexts.rememberAppContext
import org.example.pipe2.ui.theme.AppTheme

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