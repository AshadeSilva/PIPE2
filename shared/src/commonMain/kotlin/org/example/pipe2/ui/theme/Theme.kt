package org.example.pipe2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = MediumGrey,
    secondary = ChangeColor,
    tertiary = ChangeColor,
    surfaceVariant = ChangeColor,
    error = ErrorRed,
    background = Background,
    surface = MediumGrey,
    onPrimary = DarkGrey, // active alarm button text
    onSecondary = White,
    onTertiary = DarkGrey,
    onBackground = DarkGrey,
    onSurface = White,
    primaryContainer = ChangeColor,
    onPrimaryContainer = ChangeColor // Alarm log text, info bar text
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
