package org.example.pipe2.apps.student

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import org.example.pipe2.data.DummyDBCollection
import org.example.pipe2.data.account.AccountDatabaseSyncer
import org.example.pipe2.data.account.AccountLogicSyncer
import org.example.pipe2.data.LocalDBCollection
import org.example.pipe2.data.account.remote.FirebaseAccountDB
import org.example.pipe2.data.account.remote.FirebaseAuth
import org.example.pipe2.data.log.LogDatabaseSyncer
import org.example.pipe2.data.log.LogLogicSyncer
import org.example.pipe2.data.log.remote.FirebaseLogDB
import org.example.pipe2.logic.SharedApp
import org.example.pipe2.logic.user.UserType
import org.example.pipe2.ui.general.GeneralLayout
import org.example.pipe2.ui.general.AppConfig
import org.example.pipe2.ui.general.LocalAppConfig
import org.example.pipe2.ui.pages.Page
import org.example.pipe2.ui.general.theme.AppTheme
import org.example.pipe2.ui.general.theme.Student

@Composable
@Preview
fun StudentSetUp(localDb: LocalDBCollection = remember { DummyDBCollection() }) {
    val remoteAuth = remember { FirebaseAuth() }
    val accountDbSyncer = remember(localDb, remoteAuth) {
        AccountDatabaseSyncer(
            FirebaseAccountDB(),
            localDb.account,
            remoteAuth
        )
    }
    val accountLogicSyncer = remember(localDb) {
        AccountLogicSyncer(localDb.account)
    }
    val logDbSyncer = remember(localDb) {
        LogDatabaseSyncer(FirebaseLogDB(), localDb.log)
    }
    val logLogicSyncer = remember(localDb) {
        LogLogicSyncer(localDb.log)
    }
    val appContext = rememberStudentApp(
        accountDbSyncer,
        accountLogicSyncer,
        remoteAuth,
        logDbSyncer,
        logLogicSyncer
    )

    val appConfig = AppConfig(
        pages = listOf(Page.SelfRegister, Page.LogIn),
        colour = Student,
        type = UserType.Student
    )

    CompositionLocalProvider(StudentApp provides appContext,
        SharedApp provides appContext,
        LocalAppConfig provides appConfig
        ) {
        AppTheme {
            GeneralLayout()
        }
    }
}