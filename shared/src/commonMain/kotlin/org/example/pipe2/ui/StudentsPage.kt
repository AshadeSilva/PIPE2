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
import org.example.pipe2.ui.contexts.LocalAppContext
import org.example.pipe2.ui.generalLayout.TitleBar

@Composable
fun BoxScope.StudentsPage() {
    val alarmVm = LocalAppContext.current.alarm
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TitleBar()
        Text("Your Students")
        Button(onClick = { alarmVm.toggle() }) {
            Text(alarmVm.wardenActivateButtonText())
        }
    }
}