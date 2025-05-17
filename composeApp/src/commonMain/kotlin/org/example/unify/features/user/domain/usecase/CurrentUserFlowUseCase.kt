package org.example.unify.features.user.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.unify.features.user.domain.entity.User
import org.example.unify.features.user.domain.repository.UserRepo

class CurrentUserFlowUseCase(private val repo: UserRepo) {
    operator fun invoke(): Flow<Result<User?>> {
        return repo.currentUser
    }
}