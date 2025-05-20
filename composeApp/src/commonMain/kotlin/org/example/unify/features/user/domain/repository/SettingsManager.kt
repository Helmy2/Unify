package org.example.unify.features.user.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.unify.core.domain.entity.Language
import org.example.unify.core.domain.entity.ThemeMode

interface SettingsManager {
    fun getThemeMode(): Flow<ThemeMode>
    suspend fun changeTheme(mode: ThemeMode)
    suspend fun changeLanguage(language: Language)
    fun getLanguage(): Flow<Language>

    companion object {
        const val THEME_KEY = "themeKey"
        const val LANGUAGE_KEY = "languageKey"
    }
}