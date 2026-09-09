package org.example.pipe2.data.log.local

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.log.EventDetails

@Dao // represents a table
interface LogDAO {
    @Query("SELECT * FROM Log ORDER BY time ASC")
    fun getCurrentEvents(): List<EventDetails>

    @Query("SELECT * FROM Log ORDER BY time ASC")
    fun getNewEvents(): Flow<List<EventDetails>>

    @Query("DELETE FROM Log")
    suspend fun clearAll()

    @Upsert
    suspend fun upsert(event: EventDetails)
}

// Room will automatically implement these based on the annotations