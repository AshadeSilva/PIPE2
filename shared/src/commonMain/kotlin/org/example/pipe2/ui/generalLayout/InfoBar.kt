package org.example.pipe2.ui.generalLayout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.pipe2.ui.contexts.LocalAppContext
import org.example.pipe2.ui.theme.InfoBar
import org.example.pipe2.ui.theme.Typography

@Composable
fun InfoBar() {
    val context = LocalAppContext.current
    val user = context.user
    val alarmVm = context.alarm
    var accountType by remember { mutableStateOf("...") }
    
    LaunchedEffect(user) {
        accountType = user.getAccountType().name
    }

    Surface(
        color = InfoBar,
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
                text = "User: $accountType",
                style = Typography.bodyMedium
            )
            Text(
                text = "Alarm: ${alarmVm.stateName()}",
                style = Typography.bodyMedium
            )
        }
    }
}
