package org.example.pipe2.data

import org.example.pipe2.data.account.local.LocalAccountDB
import org.example.pipe2.data.account.local.RoomAccountDB
import org.example.pipe2.data.auth.local.LocalAuthDB
import org.example.pipe2.data.auth.local.RoomAuthDB
import org.example.pipe2.data.log.local.DummyLogDB
import org.example.pipe2.data.log.local.LocalLogDB
import org.example.pipe2.data.log.local.RoomLogDB

//// exists solely to pass room from android to shared
interface LocalDBCollection {
    val account: LocalAccountDB
    val auth: LocalAuthDB
    val log: LocalLogDB
}

class RoomDBCollection(room: RoomDB): LocalDBCollection {
    override val account = RoomAccountDB(room)
    override val auth = RoomAuthDB(room)
    override val log = RoomLogDB(room)
}

class DummyDBCollection(): LocalDBCollection {
    private val db = DummyLocalDB()
    override val account = db
    override val auth = db
    override val log = DummyLogDB()
}