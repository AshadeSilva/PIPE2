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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.pipe2.logic.contexts.LocalAppContext

@Composable
fun GeneralPage() {
    val ui = LocalAppContext.current.ui
    val theme = ui.theme
    val currentScreen = remember { mutableStateOf<Page>(Page.LogIn) }

    // Automatically switch screen when theme changes
    LaunchedEffect(theme) {
        currentScreen.value = theme.pages.first()
    }

    val screens: List<Page> = theme.pages

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