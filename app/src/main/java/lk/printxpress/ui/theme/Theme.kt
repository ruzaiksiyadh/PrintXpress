package lk.printxpress.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Indigo600,
    onPrimary = Color.White,
    primaryContainer = Indigo100,
    onPrimaryContainer = Indigo900,
    secondary = Emerald500,
    onSecondary = Color.White,
    secondaryContainer = Emerald50,
    onSecondaryContainer = Color(0xFF065F46),
    tertiary = Tangerine500,
    onTertiary = Color.White,
    background = Slate50,
    onBackground = Slate900,
    surface = Color.White,
    onSurface = Slate900,
    surfaceVariant = Slate100,
    onSurfaceVariant = Slate700,
    outline = Slate300,
    outlineVariant = Slate200,
    error = Rose500,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = Indigo500,
    onPrimary = Color.White,
    primaryContainer = Indigo900,
    onPrimaryContainer = Indigo100,
    secondary = Emerald400,
    onSecondary = Slate900,
    secondaryContainer = Color(0xFF064E3B),
    onSecondaryContainer = Emerald50,
    tertiary = Tangerine500,
    onTertiary = Color.White,
    background = Obsidian950,
    onBackground = Color(0xFFF1F5F9),
    surface = Slate800,
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Slate700,
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF475569),
    outlineVariant = Color(0xFF334155),
    error = Rose500,
    onError = Color.White
)

@Composable
fun PrintXpressTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
