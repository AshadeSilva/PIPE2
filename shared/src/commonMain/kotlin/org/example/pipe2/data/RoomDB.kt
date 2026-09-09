package org.example.pipe2.data

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import androidx.room3.ColumnTypeConverters
import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import org.example.pipe2.data.account.UserDetails
import org.example.pipe2.data.account.local.AccountDAO
import org.example.pipe2.data.auth.local.LoggedInDAO
import org.example.pipe2.data.auth.local.LoggedIn
import org.example.pipe2.data.log.EventDetails
import org.example.pipe2.data.log.RoomConverter
import org.example.pipe2.data.log.local.LogDAO

@Database(
    entities = [UserDetails::class, LoggedIn::class, EventDetails::class], // entity === table.{it.record.type}
    version = 3
)
@ColumnTypeConverters(RoomConverter::class)
@ConstructedBy(RoomDBConstructor::class)
abstract class RoomDB: RoomDatabase() {
    abstract fun accountDao(): AccountDAO
    abstract fun loggedInDao(): LoggedInDAO
    abstract fun logDao(): LogDAO
}

// used for iOS version
@Suppress("KotlinNoActualForExpect")
expect object RoomDBConstructor : RoomDatabaseConstructor<RoomDB> {
    override fun initialize(): RoomDB
    
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override suspend fun migrate(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `Sessions` (`uid` TEXT NOT NULL, `app` INTEGER NOT NULL, PRIMARY KEY(`app`))")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override suspend fun migrate(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `Log` (" +
                "`docId` TEXT NOT NULL, " +
                "`type` TEXT NOT NULL, " +
                "`sender` TEXT NOT NULL, " +
                "`time` INTEGER NOT NULL, " +
                "`recipient` TEXT, " +
                "`contents` TEXT, " +
                "`status` TEXT, " +
                "`student` TEXT, " +
                "PRIMARY KEY(`docId`))")
    }
}