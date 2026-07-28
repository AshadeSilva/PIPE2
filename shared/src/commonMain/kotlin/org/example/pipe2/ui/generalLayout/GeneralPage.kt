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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.pipe2.logic.AppContext
import org.example.pipe2.logic.LocalAppContext
import org.example.pipe2.ui.bars.BottomNavBar

@Composable
fun GeneralPage() {
    val currentScreen = remember { mutableStateOf<Page>(Page.LogIn) }
    val user = LocalAppContext.current.user
    val screens: List<Page> = user.accountType.pages

    Column(
        modifier = Modifier.Companion
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
    ) {
        InfoBar()
        currentScreen.value.bar()
        Column(
            modifier = Modifier.Companion
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            currentScreen.value.Contents()
        }
        BottomNavBar(screens, currentScreen)
    }
}