package org.example.unify.features.user.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.unify.core.domain.entity.ThemeMode
import org.example.unify.features.user.domain.repository.SettingsManager

class GetThemeModeUseCase(private val repo: SettingsManager) {
    operator fun invoke(): Flow<ThemeMode> {
        return repo.getThemeMode()
    }
}

