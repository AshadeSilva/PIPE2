package org.example.pipe2.ui.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.pipe2.logic.Event
import org.example.pipe2.logic.LocalAppContext
import org.example.pipe2.utils.logDebug

@Composable
fun LogPage() {
    val log = LocalAppContext.current.log

    Text(log.title, style = MaterialTheme.typography.headlineMedium)

    log.log?.events?.forEach {
        LogRow(it)
        HorizontalDivider()
    }
}

@Composable
fun LogRow(event: Event) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = event.name,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = event.printTime()
        )
    }
}
