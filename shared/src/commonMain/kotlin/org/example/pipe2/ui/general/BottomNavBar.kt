package org.example.pipe2.ui.general

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import org.example.pipe2.ui.general.GeneralLayoutContext

@Composable
fun BottomNavBar(ui: GeneralLayoutContext) {
    NavigationBar {
        for (page in ui.pages){
            NavigationBarItem(
                icon = { Icon(page.icon, "home") },
                selected = ui.currentPage == page,
                onClick = { ui.currentPage = page }
                )
        }
    }
}