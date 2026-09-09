package org.example.pipe2.ui.generalLayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.pipe2.logic.LocalAppLogicContext
import org.example.pipe2.logic.user.UserContext

@Composable
fun GeneralLayout(
    userContext: UserContext = LocalAppLogicContext.current.user,
    ui: GeneralLayoutContext = viewModel { GeneralLayoutContext(userContext) }
) {
    // will save the view models of the pages
    val saveableStateHolder = rememberSaveableStateHolder()

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
        bottomBar = {BottomNavBar(ui)}
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