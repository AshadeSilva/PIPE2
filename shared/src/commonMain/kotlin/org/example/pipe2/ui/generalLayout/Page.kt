package org.example.pipe2.ui.generalLayout

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import org.example.pipe2.ui.shared.LogPage1
import org.example.pipe2.ui.shared.LoginPage
import org.example.pipe2.ui.students.SelfRegisterPage1
import org.example.pipe2.ui.wardens.StudentsPage1

sealed class Page(val name: String, val icon: ImageVector, val Contents: @Composable () -> Unit) {
    object Students : Page("Students", Icons.Default.AccountCircle, { StudentsPage1() })

    object SelfRegister : Page("Call", Icons.Default.Home, {SelfRegisterPage1()})
    object LogIn : Page("Log In", Icons.Default.Settings, {LoginPage()})
    object LogView : Page("Log", Icons.Default.CheckCircle, {LogPage1()})
}

