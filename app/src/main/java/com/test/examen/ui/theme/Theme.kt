package com.test.examen.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val AppColorScheme = lightColorScheme(
    primary = DarkBlue,
    onPrimary = White,
    primaryContainer = LightBlue,
    onPrimaryContainer = Black,

    secondary = LightRed,
    onSecondary = White,
    secondaryContainer = DarkRed,
    onSecondaryContainer = White,

    tertiary = Info,
    onTertiary = White,
    tertiaryContainer = LightBlue,
    onTertiaryContainer = Black,

    background = Cream,
    onBackground = DarkBlue,

    surface = White,
    onSurface = DarkBlue,
    surfaceVariant = LightBlue,
    onSurfaceVariant = DarkBlue,

    error = Error,
    onError = White,

    outline = LightBlue,
    outlineVariant = LightBlue,
    inversePrimary = LightBlue,
    scrim = Black
)

data class ExtendedColors(
    val success: Color,
    val warning: Color,
    val info: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        success = Success,
        warning = Warning,
        info = Info
    )
}

@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalExtendedColors provides ExtendedColors(
            success = Success,
            warning = Warning,
            info = Info
        )
    ) {
        MaterialTheme(
            colorScheme = AppColorScheme,
            typography = AppTypography,
            content = content
        )
    }
}