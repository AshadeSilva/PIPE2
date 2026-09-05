package org.example.pipe2.data.account.remote

import kotlinx.coroutines.flow.Flow
import org.example.pipe2.data.account.UserDetails

interface RemoteAccountDB {
    fun observe(uid: String): Flow<UserDetails?>
}
