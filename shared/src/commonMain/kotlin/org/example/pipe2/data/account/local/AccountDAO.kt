package org.example.pipe2.data.account.local

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.account.UserDetails
import org.example.pipe2.logic.account.User

@Dao // represents a table
interface AccountDAO {
    @Upsert
    suspend fun upsert(user: UserDetails)

    @Query("SELECT * FROM Accounts WHERE uid = :uid")
    fun selectByUid(uid: String): Flow<UserDetails?>

    @Query("DELETE FROM Accounts WHERE uid = :uid")
    suspend fun deleteByUid(uid: String)

    @Query("SELECT * FROM Accounts WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): UserDetails?
}

// Room will automatically implement these based on the annotations