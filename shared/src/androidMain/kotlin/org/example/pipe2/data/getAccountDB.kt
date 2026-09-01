package org.example.pipe2.data

import android.content.Context // context == information about app. eg where are its files?
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.pipe2.data.account.local.AccountDB


// find/build the local database for storing account-based information
// for the iOS implementation see https://youtu.be/IHs0yPa2Nv4?t=666 How to Setup a Room DB for Kotlin Multiplatform Compose Philipp Lackner 11:06
fun getAccountDB(context: Context): AccountDB  =
    Room.databaseBuilder<AccountDB>(
        context = context.applicationContext,
        name = context.getDatabasePath("accounts.db").absolutePath,
    )
        .setDriver(BundledSQLiteDriver())
        .build()
