package org.example.pipe2.ui.generalLayout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import org.example.pipe2.data.DummyDBCollection
import org.example.pipe2.data.account.AccountDatabaseSyncer
import org.example.pipe2.data.account.AccountLogicSyncer
import org.example.pipe2.data.LocalDBCollection
import org.example.pipe2.data.account.remote.FirebaseAccountDB
import org.example.pipe2.data.auth.remote.FirebaseAuth
import org.example.pipe2.data.log.LogDatabaseSyncer
import org.example.pipe2.data.log.LogLogicSyncer
import org.example.pipe2.data.log.remote.FirebaseLogDB
import org.example.pipe2.logic.LocalAppLogicContext
import org.example.pipe2.logic.rememberAppContext
import org.example.pipe2.ui.theme.AppTheme

@Composable
@Preview
fun SetUp(localDb: LocalDBCollection = remember { DummyDBCollection() }) {
    val remoteAuth = remember { FirebaseAuth() }
    val accountDbSyncer = remember(localDb, remoteAuth) {
        AccountDatabaseSyncer(
            FirebaseAccountDB(),
            localDb.account,
            localDb.auth,
            remoteAuth
        )
    }
    val accountLogicSyncer = remember(localDb) { AccountLogicSyncer(localDb.account) }

    val logDbSyncer = remember(localDb) {
        LogDatabaseSyncer(
            FirebaseLogDB(),
            localDb.log
        )
    }

    val logLogicSyncer = remember(localDb) {
        LogLogicSyncer(
            localDb.log
        )
    }

    val appContext = rememberAppContext(
        accountDbSyncer,
        accountLogicSyncer,
        remoteAuth,
        logDbSyncer,
        logLogicSyncer
    )

    CompositionLocalProvider(LocalAppLogicContext provides appContext) {
        AppTheme {
            GeneralLayout()
        }
    }
}