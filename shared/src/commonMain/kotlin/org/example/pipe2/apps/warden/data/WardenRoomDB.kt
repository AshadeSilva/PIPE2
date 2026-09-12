package org.example.pipe2.apps.warden.data

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import androidx.room3.ColumnTypeConverters
import org.example.pipe2.data.account.UserDetails
import org.example.pipe2.data.account.local.AccountDAO
import org.example.pipe2.data.log.EventDetails
import org.example.pipe2.data.log.RoomConverter
import org.example.pipe2.data.log.local.LogDAO

@Database(
    entities = [UserDetails::class, EventDetails::class],
    version = 1
)
@ColumnTypeConverters(RoomConverter::class)
@ConstructedBy(WardenRoomDBConstructor::class)
abstract class WardenRoomDB: RoomDatabase() {
    abstract fun accountDao(): AccountDAO
    abstract fun logDao(): LogDAO
}

@Suppress("KotlinNoActualForExpect")
expect object WardenRoomDBConstructor : RoomDatabaseConstructor<WardenRoomDB> {
    override fun initialize(): WardenRoomDB
}
