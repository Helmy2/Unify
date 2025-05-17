package org.example.unify.features.user.domain.usecase

import org.example.unify.core.domain.entity.Language
import org.example.unify.features.user.domain.repository.SettingsManager

class ChangeLanguageUseCase(private val repo: SettingsManager) {
    suspend operator fun invoke(language: Language) {
        repo.changeLanguage(language)
    }
}