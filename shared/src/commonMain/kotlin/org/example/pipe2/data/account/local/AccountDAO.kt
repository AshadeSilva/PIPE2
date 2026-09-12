package org.example.pipe2.data.account.local

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.account.UserDetails

@Dao // represents a table
interface AccountDAO {
    @Upsert
    suspend fun upsert(user: UserDetails)

    @Query("DELETE FROM Accounts")
    suspend fun clear()

    @Query("SELECT * FROM Accounts LIMIT 1")
    fun getFlow(): Flow<UserDetails?>

    @Query("SELECT * FROM Accounts LIMIT 1")
    suspend fun getOnce(): UserDetails?
}

// Room will automatically implement these based on the annotations