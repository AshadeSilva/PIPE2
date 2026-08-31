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
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.pipe2.logic.LocalAppContext
import org.example.pipe2.logic.account.UserContext

@Composable
fun GeneralLayout(
    userContext: UserContext = LocalAppContext.current.user,
    ui: GeneralLayoutContext = viewModel { GeneralLayoutContext(userContext) }
) {
    // will save the view models of the pages
    val saveableStateHolder = rememberSaveableStateHolder()

    Column(
        modifier = Modifier.Companion
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
    ) {
        InfoBar(ui)
        ui.currentPage.bar(ui)
        Column(
            modifier = Modifier.Companion
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            saveableStateHolder.SaveableStateProvider(key = ui.currentPage.name) {
                ui.currentPage.Contents()
            }
        }
        BottomNavBar(ui)
    }
}