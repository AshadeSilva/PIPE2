package org.example.pipe2.data.account

import androidx.room3.Entity
import androidx.room3.PrimaryKey


// represents users as flat files as they are stored in database
// no checks, might be empty or corrupted
@Entity(tableName = "Accounts") // represents a record in a database table
data class UserDetails (
    @PrimaryKey val uid: String,
    val username: String?,
    val building: String?,
    val email: String?,
    val type: String?,
)