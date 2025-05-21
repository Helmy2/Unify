package org.example.unify.features.user.domain.usecase.login

import org.example.unify.features.user.domain.repository.UserRepo

class IsUserLongedInFlowUseCase(private val repo: UserRepo) {
    operator fun invoke() = repo.isUserLongedIn()
}


