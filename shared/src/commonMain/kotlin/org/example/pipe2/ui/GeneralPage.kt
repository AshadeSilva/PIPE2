package org.example.pipe2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.example.pipe2.data.account.AccountType
import org.example.pipe2.ui.contexts.LocalAppContext
import org.example.pipe2.ui.contexts.rememberAppContext
import org.example.pipe2.ui.generalLayout.BottomNavBar
import org.example.pipe2.ui.generalLayout.InfoBar
import org.example.pipe2.ui.generalLayout.Page
import org.example.pipe2.ui.theme.AppTheme

@Composable
@Preview
fun GeneralPage() {
    val appContext = rememberAppContext()

    CompositionLocalProvider(LocalAppContext provides appContext) {
        AppTheme {
            val currentScreen = remember { mutableStateOf<Page>(Page.LogIn) }
            val user = appContext.user

            val screens: List<Page> = user.accountType.pages

            Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .safeContentPadding()
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            InfoBar()
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                with(currentScreen.value) {
                    Content()
                }
            }
            BottomNavBar(screens, currentScreen)
        }
    }
}
}
