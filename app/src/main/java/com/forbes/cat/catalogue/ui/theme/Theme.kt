package com.forbes.cat.catalogue.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PinkDark,
    onPrimary = GreyLightest,
    primaryContainer = PinkDark,
    secondary = BlueDarkest,
    secondaryContainer = BlueDarkest,
    onSecondary = GreyLightest,
    tertiary = YellowDark,
    tertiaryContainer = DarkGreyOverlay,
    background = GreyDarkest,
    onBackground = GreyLightest,
)

private val LightColorScheme = lightColorScheme(
    primary = PinkLightest,
    onPrimary = GreyDarkest,
    primaryContainer = PinkLightest,
    secondary = BlueLight,
    secondaryContainer = BlueLight,
    onSecondary = GreyDarkest,
    tertiary = YellowLight,
    tertiaryContainer = LightGreyOverlay,
    background = GreyLightest,
    onBackground = GreyDarkest

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun CATalogueTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}