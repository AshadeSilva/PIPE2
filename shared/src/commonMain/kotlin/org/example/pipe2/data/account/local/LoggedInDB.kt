package org.example.pipe2.data.account.local

import androidx.room3.ConstructedBy
import androidx.room3.Dao
import androidx.room3.Database
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.room3.Query
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import androidx.room3.Upsert
import org.example.pipe2.data.account.UserDetails

// record
@Entity(tableName = "Sessions") // represents a record in a database table
data class LoggedIn (val uid: String) {
    @PrimaryKey var app: Int = 0
}

// table
@Dao
interface LoggedInDAO {
    @Upsert
    suspend fun rememberUser(account: LoggedIn)

    @Query("SELECT * FROM Sessions WHERE app = 0 LIMIT 1")
    suspend fun getUser(): LoggedIn?

    @Query("DELETE FROM Sessions WHERE app = 0")
    suspend fun forgetUser()
}







