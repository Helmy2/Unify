package org.example.unify.features.user.presentation.setting

import org.example.unify.core.domain.entity.Language
import org.example.unify.core.domain.entity.ThemeMode
import org.example.unify.features.user.domain.entity.User

data class SettingsState(
    val user: User? = null,
    val showEditNameDialog: Boolean = false,
    val showEditProfilePictureDialog: Boolean = false,
    val showThemeDialog: Boolean = false,
    val showLanguageDialog: Boolean = false,
    val name: String = "",
    val profilePictureLoading: Boolean = false,
    val themeMode: ThemeMode = ThemeMode.System,
    val language: Language = Language.English,
)