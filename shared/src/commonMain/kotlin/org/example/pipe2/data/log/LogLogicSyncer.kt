package org.example.pipe2.data.log

import androidx.compose.runtime.snapshots.SnapshotStateList
import org.example.pipe2.data.log.local.LocalLogDB
import org.example.pipe2.logic.alarm.Event

class LogLogicSyncer(val local: LocalLogDB) {

//    eventually responsible for filtering/ formatting the messages to the user
    // altho this is mostly done in firestore rules - implement when implementing messaging

    suspend fun loadLog(events: SnapshotStateList<Event>) {
        local.watchLog().collect { newList ->
            // If the list was cleared in the DB, clear it in the UI
            if (newList.isEmpty() && events.isNotEmpty()) {
                events.clear()
                return@collect
            }

            // Find events in the database that aren't in our UI list yet
            val existingIds = events.map { it.id }.toSet()
            val newItems = newList
                .filter { it.docId !in existingIds }
                .map { it.toEvent() }

            if (newItems.isNotEmpty()) {
                events.addAll(newItems)
            }
        }
    }

}
