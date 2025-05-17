package org.example.unify.features.user.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.example.unify.core.domain.entity.Language
import org.example.unify.core.domain.entity.ThemeMode
import org.example.unify.features.user.domain.repository.SettingsManager
import kotlin.coroutines.cancellation.CancellationException

class SettingsManagerImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsManager {
    companion object {
        private const val THEME_KEY = "themeKey"
        private const val LANGUAGE_KEY = "languageKey"
    }

    override fun getThemeMode(): Flow<ThemeMode> {
        return dataStore.data.map {
            val theme = it[stringPreferencesKey(THEME_KEY)] ?: ThemeMode.System.name
            ThemeMode.valueOf(theme)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun changeTheme(
        mode: ThemeMode
    ) {
        return withContext(Dispatchers.IO) {
            try {
                dataStore.edit {
                    it[stringPreferencesKey(THEME_KEY)] = mode.name
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()
            }
        }
    }

    override suspend fun changeLanguage(
        language: Language
    ) {
        return withContext(Dispatchers.IO) {
            try {
                dataStore.edit {
                    it[stringPreferencesKey(LANGUAGE_KEY)] = language.name
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()
            }
        }
    }

    override fun getLanguage(): Flow<Language> {
        return dataStore.data.map {
            val language = it[stringPreferencesKey(LANGUAGE_KEY)] ?: Language.English.name
            Language.valueOf(language)
        }.flowOn(Dispatchers.IO)
    }
}

