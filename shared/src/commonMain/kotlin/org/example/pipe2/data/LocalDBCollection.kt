package org.example.pipe2.data

import org.example.pipe2.data.account.local.AccountDAO
import org.example.pipe2.data.account.local.DummyLocalDB
import org.example.pipe2.data.account.local.LocalAccountDB
import org.example.pipe2.data.account.local.RoomAccountDB
import org.example.pipe2.data.log.local.DummyLogDB
import org.example.pipe2.data.log.local.LocalLogDB
import org.example.pipe2.data.log.local.LogDAO
import org.example.pipe2.data.log.local.RoomLogDB

//// exists solely to pass room from android to shared
interface LocalDBCollection {
    val account: LocalAccountDB
    val log: LocalLogDB
}

class RoomDBCollection(
    accountDao: AccountDAO,
    logDao: LogDAO
): LocalDBCollection {
    override val account = RoomAccountDB(accountDao)
    override val log = RoomLogDB(logDao)
}

class DummyDBCollection(): LocalDBCollection {
    override val account = DummyLocalDB()
    override val log = DummyLogDB()
}