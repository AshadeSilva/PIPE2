package org.example.pipe2.data.account.local

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import org.example.pipe2.data.account.UserDetails

@Database(
    entities = [UserDetails::class], // entity === table
    version = 1
)
@ConstructedBy(AccountDBConstructor::class)
abstract class AccountDB: RoomDatabase() {
    abstract fun accountDao(): AccountDAO
}

// used for iOS version
@Suppress("KotlinNoActualForExpect")
expect object AccountDBConstructor : RoomDatabaseConstructor<AccountDB> {
    override fun initialize(): AccountDB
}







