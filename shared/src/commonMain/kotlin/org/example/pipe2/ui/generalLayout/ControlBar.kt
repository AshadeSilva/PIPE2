package org.example.pipe2.ui.generalLayout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.pipe2.ui.contexts.LocalAppContext

@Composable
fun ControlBar() {
    val context = LocalAppContext.current
    val alarmVm = context.alarm

    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = alarmVm.stateName(),
                style = MaterialTheme.typography.bodyLarge
            )

            Button(onClick = { alarmVm.toggle() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(alarmVm.wardenActivateButtonText())
            }
        }
    }
}
