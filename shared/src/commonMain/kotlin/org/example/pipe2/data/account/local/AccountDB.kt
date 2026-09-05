package org.example.pipe2.data.account.local

import androidx.room3.AutoMigration
import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import org.example.pipe2.data.account.UserDetails

@Database(
    entities = [UserDetails::class, LoggedIn::class], // entity === table.{it.record.type}
    version = 2
)
@ConstructedBy(AccountDBConstructor::class)
abstract class AccountDB: RoomDatabase() {
    abstract fun accountDao(): AccountDAO
    abstract fun loggedInDao(): LoggedInDAO
}

// used for iOS version
@Suppress("KotlinNoActualForExpect")
expect object AccountDBConstructor : RoomDatabaseConstructor<AccountDB> {
    override fun initialize(): AccountDB
    
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override suspend fun migrate(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `Sessions` (`uid` TEXT NOT NULL, `app` INTEGER NOT NULL, PRIMARY KEY(`app`))")
    }
}







