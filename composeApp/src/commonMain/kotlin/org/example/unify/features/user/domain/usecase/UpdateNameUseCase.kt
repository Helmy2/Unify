package org.example.unify.features.user.domain.usecase

import org.example.unify.features.user.domain.repository.UserRepo

class UpdateNameUseCase(private val repo: UserRepo) {
    suspend operator fun invoke(name: String): Result<Unit> {
        return repo.updateDisplayName(name)
    }
}