package org.example.pipe2.ui.generalLayout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import org.example.pipe2.data.account.DatabaseSyncer
import org.example.pipe2.data.account.UserSyncer
import org.example.pipe2.data.account.local.DummyLocalAccountDB
import org.example.pipe2.data.account.local.LocalAccountDB
import org.example.pipe2.data.account.remote.FirebaseAccountDB
import org.example.pipe2.data.account.remote.FirebaseAuth
import org.example.pipe2.data.account.remote.RemoteAccountDB
import org.example.pipe2.logic.LocalAppLogicContext
import org.example.pipe2.logic.rememberAppContext
import org.example.pipe2.ui.theme.AppTheme

@Composable
@Preview
fun SetUp(localDb: LocalAccountDB = DummyLocalAccountDB()) {
    val remoteDb: RemoteAccountDB = FirebaseAccountDB()
    val remoteAuth = FirebaseAuth()
    val dbSyncer = DatabaseSyncer(remoteDb, localDb, remoteAuth)
    val userSyncer = UserSyncer(localDb)

    val appContext = rememberAppContext(dbSyncer, userSyncer, remoteAuth)

    CompositionLocalProvider(LocalAppLogicContext provides appContext) {
        AppTheme {
            GeneralLayout()
        }
    }
}