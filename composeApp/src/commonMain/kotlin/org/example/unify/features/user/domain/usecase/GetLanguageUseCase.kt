package org.example.unify.features.user.domain.usecase

import org.example.unify.features.user.domain.repository.SettingsManager

class GetLanguageUseCase(private val repo: SettingsManager) {
    operator fun invoke() = repo.getLanguage()
}