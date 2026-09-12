package org.example.pipe2.data.log.local

import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.log.EventDetails

class RoomLogDB(private val log: LogDAO): LocalLogDB {
    override suspend fun getLog(): List<EventDetails> = log.getCurrentEvents()
    override suspend fun watchLog(): Flow<List<EventDetails>> = log.getNewEvents()
    override suspend fun clearLog() = log.clearAll()
    override suspend fun appendLog(eventDetails: EventDetails) =
        log.upsert(eventDetails)
}