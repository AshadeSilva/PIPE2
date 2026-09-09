package org.example.pipe2.data.auth.local

import org.example.pipe2.data.RoomDB


class RoomAuthDB(val room: RoomDB): LocalAuthDB {
    private val loggedIn = room.loggedInDao()
    override suspend fun rememberUser(uid: String) = loggedIn.rememberUser(LoggedIn(uid))
    override suspend fun forgetUser() = loggedIn.forgetUser()
    override suspend fun getUser(): String? = loggedIn.getUser()?.uid
}