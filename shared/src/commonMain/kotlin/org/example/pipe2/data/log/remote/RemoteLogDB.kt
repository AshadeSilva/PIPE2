package org.example.pipe2.data.log.remote

import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.log.EventDetails

interface RemoteLogDB {
    fun observe(alarmId: String): Flow<EventDetails>
}