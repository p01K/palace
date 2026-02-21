package com.pk.palace.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = FitnessDarkBlue,
    secondary = FitnessDarkGreen,
    tertiary = FitnessDarkCream,
    background = Color(0xFF001F25),
    surface = Color(0xFF001F25),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = FitnessDarkBlue,
    onBackground = Color(0xFFE6F1F5),
    onSurface = Color(0xFFE6F1F5),
)

private val LightColorScheme = lightColorScheme(
    primary = FitnessBlue,
    secondary = FitnessGreen,
    tertiary = FitnessLightCream,
    background = FitnessCream,
    surface = FitnessCream,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun PalaceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}