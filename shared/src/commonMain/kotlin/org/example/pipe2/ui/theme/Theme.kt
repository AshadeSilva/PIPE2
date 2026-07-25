package org.example.pipe2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = InfoBar,
    secondary = ChangeColor,
    tertiary = ChangeColor,
    surfaceVariant = ChangeColor,
    error = ErrorRed,
    background = Background,
    surface = DarkGrey,
    onPrimary = White, // active alarm button text
    onSecondary = ChangeColor,
    onTertiary = ChangeColor,
    onBackground = ChangeColor,
    onSurface = White,
    primaryContainer = ChangeColor,
    onPrimaryContainer = ChangeColor
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
