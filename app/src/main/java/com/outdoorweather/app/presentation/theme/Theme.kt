package com.outdoorweather.app.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = SkyBlue,
    onPrimary = OnSkyBlue,
    primaryContainer = SkyBlueContainer,
    onPrimaryContainer = SkyBlueDark,
    secondary = WarmAmber,
    onSecondary = OnAmber,
    secondaryContainer = WarmAmberContainer,
    onSecondaryContainer = WarmAmberDark,
    tertiary = SageGreen,
    onTertiary = OnGreen,
    tertiaryContainer = SageGreenContainer,
    onTertiaryContainer = SageGreenDark,
    background = AliceBlue,
    onBackground = OnAliceBlue,
    surface = SurfaceBlue,
    onSurface = OnSurface,
    error = ErrorRed,
    onError = OnErrorRed,
    errorContainer = ErrorRedContainer,
    onErrorContainer = ErrorRed
)

private val DarkColorScheme = darkColorScheme(
    primary = SkyBlueDarkTheme,
    onPrimary = SkyBlueDark,
    primaryContainer = SkyBlueDark,
    onPrimaryContainer = SkyBlueLight,
    secondary = AmberDarkTheme,
    onSecondary = WarmAmberDark,
    secondaryContainer = WarmAmberDark,
    onSecondaryContainer = WarmAmberLight,
    tertiary = GreenDarkTheme,
    onTertiary = SageGreenDark,
    tertiaryContainer = SageGreenDark,
    onTertiaryContainer = SageGreenLight,
    background = DarkBackground,
    onBackground = AliceBlue,
    surface = DarkSurface,
    onSurface = AliceBlue
)

@Composable
fun OutdoorPlannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
