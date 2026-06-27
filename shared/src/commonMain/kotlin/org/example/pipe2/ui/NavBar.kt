package org.example.pipe2.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun BottomNavBar(pages: List<Page>, currentPage: MutableState<Page>) {
    NavigationBar {
        for (page in pages){
            NavigationBarItem(
                icon = { Icon(page.icon, "home") },
                label = {"screen"},
                selected = currentPage.value == page,
                onClick = { currentPage.value = page }
                )
        }
    }
}