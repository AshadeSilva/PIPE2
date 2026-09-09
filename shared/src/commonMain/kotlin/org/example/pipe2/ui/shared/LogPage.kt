package org.example.pipe2.ui.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.pipe2.logic.LocalAppLogicContext
import org.example.pipe2.logic.alarm.Event

@Composable
fun LogPage() {
    val log = LocalAppLogicContext.current.alarm
    val sortedEvents = log.events.sortedBy { it.time }
    val listState = rememberLazyListState()

    // Automatically scroll to bottom when new events are added
    LaunchedEffect(sortedEvents.size) {
        if (sortedEvents.isNotEmpty()) {
            listState.animateScrollToItem(sortedEvents.size)
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.padding(16.dp)
    ) {
        item {Text(log.title, style = MaterialTheme.typography.headlineMedium)}
        items(items = sortedEvents, key = { it.id }) { event ->
            LogRow(event)
            HorizontalDivider()
        }
    }
}

@Composable
fun LogRow(event: Event) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = event.name, modifier = Modifier.weight(1f))
        Text(text = event.printTime())
    }
}
