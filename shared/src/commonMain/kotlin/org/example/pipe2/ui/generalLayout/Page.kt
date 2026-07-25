package org.example.pipe2.ui.generalLayout

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import org.example.pipe2.ui.LogInPage
import org.example.pipe2.ui.StudentsPage

sealed class Page(val name: String, val icon: ImageVector) {
    @Composable
    abstract fun BoxScope.Content()

    object Students : Page("Students", Icons.Default.AccountCircle) {
        @Composable
        override fun BoxScope.Content() = StudentsPage()
    }

    object SelfRegister : Page("Call", Icons.Default.Home) {
        @Composable
        override fun BoxScope.Content() = SelfRegisterPage()
    }

    object LogIn : Page("Log In", Icons.Default.Settings) {
        @Composable
        override fun BoxScope.Content() = LogInPage()
    }
}



@Composable
fun BoxScope.SelfRegisterPage() {
    Text("Register as Safe")
}