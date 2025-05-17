package org.example.unify.features.user.domain.usecase

import org.example.unify.core.domain.entity.ThemeMode
import org.example.unify.features.user.domain.repository.SettingsManager

class ChangeThemeModeUseCase(private val repo: SettingsManager) {
    suspend operator fun invoke(mode: ThemeMode) {
        repo.changeTheme(mode)
    }
}

