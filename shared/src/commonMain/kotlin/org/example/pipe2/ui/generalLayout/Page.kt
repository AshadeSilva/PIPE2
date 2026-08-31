package org.example.pipe2.ui.generalLayout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import org.example.pipe2.ui.shared.LogPage
import org.example.pipe2.ui.shared.LoginPage
import org.example.pipe2.ui.students.StatusBar
import org.example.pipe2.ui.wardens.ControlBar

sealed class Page(val name: String, val icon: ImageVector, val Contents: @Composable (() -> Unit), val bar: @Composable ((GeneralLayoutContext) -> Unit)) {
    object Students : Page("Students", Icons.Default.AccountCircle, { StudentsPage() }, {ControlBar(it)})
    object SelfRegister : Page("Call", Icons.Default.Home, {SelfRegisterPage()}, {StatusBar()})
    object LogIn : Page("Log In", Icons.Default.Settings, {LoginPage()}, {})
    object LogView : Page("Log", Icons.Default.CheckCircle, {LogPage()}, {StatusBar()})
}

@Composable
fun StudentsPage() {
    Text("Your Students")
}

@Composable
fun SelfRegisterPage() {
    Text("Your Students")
}