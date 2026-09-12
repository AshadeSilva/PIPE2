package org.example.pipe2.ui.general

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import org.example.pipe2.logic.user.UserType
import org.example.pipe2.ui.pages.Page

data class AppConfig(
    val pages: List<Page>,
    val colour: Color,
    val type: UserType // "warden" or "student"
)

val LocalAppConfig = staticCompositionLocalOf<AppConfig> {
    error("No AppConfig provided")
}