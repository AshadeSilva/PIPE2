package org.example.pipe2.ui.generalLayout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.pipe2.oldLogic.LocalAppContext

@Composable
fun InfoBar() {
    val context = LocalAppContext.current
    val user = context.user

    Surface(
        color = user.theme.colour,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = user.currentUser?.username ?: "Not Logged In",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
