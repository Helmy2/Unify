package org.example.unify.features.user.domain.usecase

import org.example.unify.features.user.domain.repository.UserRepo


class RegisterUseCase(private val repo: UserRepo) {
    suspend operator fun invoke(email: String, password: String, name: String): Result<Unit> {
        return repo.createUserWithEmailAndPassword(name, email, password)
    }
}