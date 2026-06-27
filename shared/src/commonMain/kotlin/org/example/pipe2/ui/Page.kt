package org.example.pipe2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

sealed class Page(val name: String) {
    @Composable
    abstract fun BoxScope.Content()

    object Students : Page("Students") {
        @Composable
        override fun BoxScope.Content() = StudentsPage()
    }

    object SelfRegister : Page("Call") {
        @Composable
        override fun BoxScope.Content() = SelfRegisterPage()
    }

    object LogIn : Page("Log In") {
        @Composable
        override fun BoxScope.Content() = LogInPage()
    }
}

@Composable
fun BoxScope.StudentsPage() {
    val alarmVm = remember { AlarmViewModel() }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Your Students")
        Button(onClick = { alarmVm.toggle() }) {
            Text(alarmVm.wardenActivateButtonText())
        }
    }
}

@Composable
fun BoxScope.SelfRegisterPage() {
    Text("Register as Safe")
}

@Composable
fun BoxScope.LogInPage() {
    Text("Who are You?")
}
