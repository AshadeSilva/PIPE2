package org.example.pipe2.data.log.local

import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.account.UserDetails
import org.example.pipe2.data.log.EventDetails
import org.example.pipe2.logic.alarm.Event

interface LocalLogDB {

    suspend fun getLog(): List<EventDetails>
    suspend fun watchLog(): Flow<List<EventDetails>>
    suspend fun clearLog()
    suspend fun appendLog(eventDetails: EventDetails)
}