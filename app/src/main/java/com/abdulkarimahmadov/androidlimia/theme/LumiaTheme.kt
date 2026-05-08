package com.abdulkarimahmadov.androidlimia.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.abdulkarimahmadov.androidlimia.domain.LumiaThemeMode
import com.abdulkarimahmadov.androidlimia.domain.ThemeSettings

@Composable
fun LumiaTheme(settings: ThemeSettings, content: @Composable () -> Unit) {
    val useDark = when (settings.mode) {
        LumiaThemeMode.SYSTEM -> isSystemInDarkTheme()
        LumiaThemeMode.LIGHT -> false
        LumiaThemeMode.DARK_AMOLED -> true
    }

    val accent = Color(settings.accent.hex)
    val colorScheme = if (useDark) {
        darkColorScheme(
            primary = accent,
            secondary = accent,
            background = Color.Black,
            surface = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        )
    } else {
        lightColorScheme(
            primary = accent,
            secondary = accent,
            background = Color(0xFFF5F7FA),
            surface = Color.White,
            onBackground = Color(0xFF111111),
            onSurface = Color(0xFF111111)
        )
    }

    MaterialTheme(colorScheme = colorScheme, typography = LumiaTypography.scaled(settings.fontScale), content = content)
}
