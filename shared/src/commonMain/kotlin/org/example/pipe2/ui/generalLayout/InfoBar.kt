package org.example.pipe2.ui.generalLayout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.pipe2.logic.LocalAppLogicContext

@Composable
fun InfoBar(ui: GeneralLayoutContext) {
    Surface(
        color = ui.theme.colour,
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
                text = LocalAppLogicContext.current.user.currentUser?.username ?: "Not Logged In",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
