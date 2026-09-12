package org.example.pipe2.apps.warden.data

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.pipe2.data.RoomDBCollection

fun getWardenRoomDB(context: Context): RoomDBCollection {
    val db = Room.databaseBuilder<WardenRoomDB>(
        context = context.applicationContext,
        name = context.getDatabasePath("warden_accounts.db").absolutePath,
        factory = { WardenRoomDBConstructor.initialize() }
    )
        .setDriver(BundledSQLiteDriver())
        .build()
    return RoomDBCollection(db.accountDao(), db.logDao())
}
