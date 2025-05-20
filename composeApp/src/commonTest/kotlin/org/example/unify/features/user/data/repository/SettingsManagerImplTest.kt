package org.example.unify.features.user.data.repository


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.example.unify.clearTestDataStoreFile
import org.example.unify.core.domain.entity.Language
import org.example.unify.core.domain.entity.ThemeMode
import org.example.unify.createTestDataStore
import org.example.unify.createTestScope
import org.example.unify.features.user.domain.repository.SettingsManager
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class SettingsManagerImplTest {

    private val testDispatcher = StandardTestDispatcher()
    private val runTestScope = TestScope(testDispatcher)

    private lateinit var dataStoreScope: CoroutineScope
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var settingsManager: SettingsManager

    private val testInstanceName = "settingsManagerImplTestInstance"

    @BeforeTest
    fun setUp() {
        dataStoreScope = createTestScope()
        dataStore = createTestDataStore(testName = testInstanceName, scope = dataStoreScope)
        settingsManager = SettingsManagerImpl(dataStore)
    }

    @AfterTest
    fun tearDown() {
        dataStoreScope.cancel()
        clearTestDataStoreFile(testName = testInstanceName)
    }

    @Test
    fun `getThemeMode returns System by default`() = runTestScope.runTest {
        val themeMode = settingsManager.getThemeMode().first()
        assertEquals(ThemeMode.System, themeMode)
    }

    @Test
    fun `changeTheme and getThemeMode work correctly`() = runTestScope.runTest {
        val newTheme = ThemeMode.Dark
        settingsManager.changeTheme(newTheme)
        val themeMode = settingsManager.getThemeMode().first()
        assertEquals(newTheme, themeMode)

        val newTheme2 = ThemeMode.Light
        settingsManager.changeTheme(newTheme2)
        val themeMode2 = settingsManager.getThemeMode().first()
        assertEquals(newTheme2, themeMode2)
    }

    @Test
    fun `getLanguage returns English by default`() = runTestScope.runTest {
        val language = settingsManager.getLanguage().first()
        assertEquals(Language.English, language)
    }

    @Test
    fun `changeLanguage and getLanguage work correctly`() = runTestScope.runTest {
        val newLanguage = Language.Arabic
        settingsManager.changeLanguage(newLanguage)
        val language = settingsManager.getLanguage().first()
        assertEquals(newLanguage, language)

        val newLanguage2 = Language.English
        settingsManager.changeLanguage(newLanguage2)
        val language2 = settingsManager.getLanguage().first()
        assertEquals(newLanguage2, language2)
    }

    @Test
    fun `getThemeMode reads from existing DataStore value`() = runTestScope.runTest {
        dataStore.edit { settings ->
            settings[stringPreferencesKey(SettingsManager.THEME_KEY)] = ThemeMode.Light.name
        }
        val themeMode = settingsManager.getThemeMode().first()
        assertEquals(ThemeMode.Light, themeMode)
    }

    @Test
    fun `getLanguage reads from existing DataStore value`() = runTestScope.runTest {
        dataStore.edit { settings ->
            settings[stringPreferencesKey(SettingsManager.LANGUAGE_KEY)] = Language.Arabic.name
        }
        val language = settingsManager.getLanguage().first()
        assertEquals(Language.Arabic, language)
    }

    @Test
    fun `getThemeMode handles invalid stored theme value and returns default`() = runTestScope.runTest {
        dataStore.edit { settings ->
            settings[stringPreferencesKey(SettingsManager.THEME_KEY)] = "INVALID_THEME_VALUE"
        }
        val themeMode = settingsManager.getThemeMode().first()
        assertEquals(ThemeMode.System, themeMode, "Should default to System for invalid stored theme")
    }

    @Test
    fun `getLanguage handles invalid stored language value and returns default`() = runTestScope.runTest {
        dataStore.edit { settings ->
            settings[stringPreferencesKey(SettingsManager.LANGUAGE_KEY)] = "INVALID_LANGUAGE_VALUE"
        }
        val language = settingsManager.getLanguage().first()
        assertEquals(Language.English, language, "Should default to English for invalid stored language")
    }

    @Test
    fun `changeTheme updates underlying DataStore`() = runTestScope.runTest {
        val newTheme = ThemeMode.Dark
        settingsManager.changeTheme(newTheme)

        val preferences = dataStore.data.first()
        assertEquals(ThemeMode.Dark.name, preferences[stringPreferencesKey(SettingsManager.THEME_KEY)])
    }

    @Test
    fun `changeLanguage updates underlying DataStore`() = runTestScope.runTest {
        val newLanguage = Language.Arabic
        settingsManager.changeLanguage(newLanguage)

        val preferences = dataStore.data.first()
        assertEquals(Language.Arabic.name, preferences[stringPreferencesKey(SettingsManager.LANGUAGE_KEY)])
    }
}