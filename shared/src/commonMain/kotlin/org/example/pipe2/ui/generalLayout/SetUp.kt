package org.example.pipe2.ui.generalLayout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import org.example.pipe2.data.account.local.AccountDAO
import org.example.pipe2.data.account.local.DummyLocalAccountDB
import org.example.pipe2.data.account.local.LocalAccountDB
import org.example.pipe2.logic.LocalAppContext
import org.example.pipe2.logic.rememberAppContext
import org.example.pipe2.ui.theme.AppTheme

@Composable
@Preview
fun SetUp(db: LocalAccountDB = DummyLocalAccountDB()) {
    val appContext = rememberAppContext(db)

    CompositionLocalProvider(LocalAppContext provides appContext) {
        AppTheme {
            GeneralLayout()
        }
    }
}