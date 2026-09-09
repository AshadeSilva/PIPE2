package org.example.pipe2.data.log.local

import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.RoomDB
import org.example.pipe2.data.account.UserDetails
import org.example.pipe2.data.account.UserNotFoundError
import org.example.pipe2.data.log.EventDetails
import org.example.pipe2.logic.alarm.Event
import org.example.pipe2.utils.logDebug

class RoomLogDB(room: RoomDB): LocalLogDB {
    private val log = room.logDao()
    override suspend fun getLog(): List<EventDetails> = log.getCurrentEvents()
    override suspend fun watchLog(): Flow<List<EventDetails>> = log.getNewEvents()
    override suspend fun clearLog() = log.clearAll()
    override suspend fun appendLog(eventDetails: EventDetails) =
        log.upsert(eventDetails)
}