package org.example.pipe2.ui.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.pipe2.logic.SharedApp

@Composable
fun GeneralLayout() {
    // will save the view models of the pages
    val user = SharedApp.current.user
    val saveableStateHolder = rememberSaveableStateHolder()
    val config = LocalAppConfig.current

    val ui = viewModel { GeneralLayoutContext(config, user) }
    CompositionLocalProvider(LocalAppConfig provides config) {
        Scaffold(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .safeContentPadding()
                .fillMaxSize(),
            topBar = {
                Column {
                    InfoBar(ui)
                    ui.currentPage.bar(ui)
                }
            },
            bottomBar = { BottomNavBar(ui) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                saveableStateHolder.SaveableStateProvider(key = ui.currentPage.name) {
                    ui.currentPage.contents()
                }
            }
        }
    }
}
