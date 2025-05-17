package org.example.unify.features.user.domain.usecase

import org.example.unify.features.user.domain.repository.UserRepo

class GetDisplayNameUseCase(private val userRepo: UserRepo) {
    suspend operator fun invoke(id: String) = userRepo.getUserById(id)
}