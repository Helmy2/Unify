package org.example.unify.features.user.presentation.setting

import org.example.unify.core.domain.entity.Language
import org.example.unify.core.domain.entity.ThemeMode

sealed interface SettingsEvent {
    data object Logout : SettingsEvent
    data class UpdateEditeNameDialog(val show: Boolean) : SettingsEvent
    data class UpdateThemeDialog(val show: Boolean) : SettingsEvent
    data class UpdateLanguageDialog(val show: Boolean) : SettingsEvent
    data class UpdateName(val name: String) : SettingsEvent
    data class UpdateThemeMode(val mode: ThemeMode) : SettingsEvent
    data class UpdateLanguage(val language: Language) : SettingsEvent
    data object ConfirmUpdateName : SettingsEvent
}