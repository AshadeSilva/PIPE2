package org.example.pipe2.ui.generalLayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.pipe2.data.account.LogListener
import org.example.pipe2.ui.bars.BottomNavBar
import org.example.pipe2.ui.theme.AppTheme
import org.example.pipe2.logic.LocalAppContext
import org.example.pipe2.logic.Log
import org.example.pipe2.logic.rememberAppContext

@Composable
@Preview
fun GeneralPage() {
    val logListener = LogListener("example_alarm")
    val log = Log(logListener)
    val appContext = rememberAppContext(log)

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
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                InfoBar()
                currentScreen.value.bar()
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    currentScreen.value.Contents()
                }
                BottomNavBar(screens, currentScreen)
            }
        }
    }
}
