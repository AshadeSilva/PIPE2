package org.example.pipe2.ui.generalLayout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.example.pipe2.ui.shared.LogPage
import org.example.pipe2.ui.shared.LoginPage
import org.example.pipe2.ui.students.StatusBar
import org.example.pipe2.ui.wardens.ControlBar

sealed class Page(
    val name: String, val icon: ImageVector,
    val contents: @Composable (() -> Unit),
    val bar: @Composable ((GeneralLayoutContext) -> Unit)
) {
    object Students : Page("Students", Icons.Default.AccountCircle, { StudentsPage() }, {ControlBar(it)})
    object SelfRegister : Page("Call", Icons.Default.Home, {SelfRegisterPage()}, {StatusBar()})
    object LogIn : Page("Log In", Icons.Default.Settings, {LoginPage()}, {})
    object LogView : Page("Log", Icons.Default.CheckCircle, {LogPage()}, {StatusBar()})

}

@Composable
fun StudentsPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Your Students")
    }
    
}

@Composable
fun SelfRegisterPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Your Students")
    }
}