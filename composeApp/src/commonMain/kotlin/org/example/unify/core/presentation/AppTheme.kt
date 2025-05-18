package org.example.unify.core.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.unify.core.domain.entity.Language
import org.example.unify.core.domain.entity.ThemeMode
import org.example.unify.core.util.LocalAppLocale
import org.example.unify.features.user.domain.usecase.GetLanguageUseCase
import org.example.unify.features.user.domain.usecase.GetThemeModeUseCase
import org.koin.compose.koinInject

val LightColorScheme: ColorScheme = lightColorScheme()
val DarkColorScheme: ColorScheme = darkColorScheme()


val Shapes = Shapes(
    extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    small = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    large = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
    extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val getThemeModeUseCase: GetThemeModeUseCase = koinInject()
    val getLanguageUseCase: GetLanguageUseCase = koinInject()
    val mode =
        getThemeModeUseCase().collectAsStateWithLifecycle(ThemeMode.System).value
    val language = getLanguageUseCase().collectAsStateWithLifecycle(Language.English).value

    val systemIsDark = remember(mode) {
        when (mode) {
            ThemeMode.Light -> false
            ThemeMode.Dark -> true
            ThemeMode.System -> isDark
        }
    }

    SystemBarAppearance(!systemIsDark)

    CompositionLocalProvider(
        LocalLayoutDirection provides language.layoutDirection,
        LocalAppLocale provides language.code,
    ) {
        MaterialTheme(
            colorScheme = getDynamicColorScheme(
                systemIsDark,
                DarkColorScheme,
                LightColorScheme,
            ),
            shapes = Shapes,
            content = { Surface(content = content) },
        )
    }
}

@Composable
expect fun getDynamicColorScheme(
    darkTheme: Boolean,
    darkColorScheme: ColorScheme,
    lightColorScheme: ColorScheme,
): ColorScheme

@Composable
expect fun SystemBarAppearance(isDark: Boolean)

