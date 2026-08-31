package org.example.pipe2.ui.generalLayout

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import org.example.pipe2.ui.generalLayout.GeneralLayoutContext

@Composable
fun BottomNavBar(ui: GeneralLayoutContext) {
    NavigationBar {
        for (page in ui.theme.pages){
            NavigationBarItem(
                icon = { Icon(page.icon, "home") },
                selected = ui.currentPage == page,
                onClick = { ui.currentPage = page }
                )
        }
    }
}