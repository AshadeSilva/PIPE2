package org.example.pipe2.apps.student.data

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.pipe2.data.RoomDBCollection

fun getStudentRoomDB(context: Context): RoomDBCollection {
    val db = Room.databaseBuilder<StudentRoomDB>(
        context = context.applicationContext,
        name = context.getDatabasePath("student_accounts.db").absolutePath,
        factory = { StudentRoomDBConstructor.initialize() }
    )
        .setDriver(BundledSQLiteDriver())
        .build()
    return RoomDBCollection(db.accountDao(), db.logDao())
}
