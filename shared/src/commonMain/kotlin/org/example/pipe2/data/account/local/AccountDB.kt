package org.example.pipe2.data.account.local

import androidx.room3.Database
import androidx.room3.RoomDatabase
import org.example.pipe2.data.account.UserDetails

@Database(
    entities = [UserDetails::class], // entity === table
    version = 1
)
abstract class AccountDB: RoomDatabase() {
    abstract fun accountDao(): AccountDAO
}









