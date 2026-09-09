package org.example.pipe2.data.log.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.example.pipe2.data.log.EventDetails
import org.example.pipe2.utils.logDebug

class DummyLogDB(): LocalLogDB {
    private var events = MutableStateFlow<List<EventDetails>>(emptyList())

    override suspend fun appendLog(eventDetails: EventDetails) {
            try {
                events.update { it + eventDetails }
            } catch (e: Exception) {
                logDebug("ASHADEBUG", "from dummy log db append log")
                logDebug("ASHADEBUG", e.message.toString())
                throw e
            }
        }

    override suspend fun getLog(): List<EventDetails> = events.value

    override suspend fun watchLog(): Flow<List<EventDetails>> = events

    override suspend fun clearLog() {
        events = MutableStateFlow(emptyList())
    }

}