package org.example.unify.features.user.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.unify.features.user.domain.entity.User

interface UserRepo {
    val currentUser: Flow<Result<User?>>

    fun isUserLongedIn(): Flow<Result<Boolean>>

    suspend fun signInWithEmailAndPassword(
        email: String, password: String
    ): Result<Unit>

    suspend fun createUserWithEmailAndPassword(
        name: String, email: String, password: String
    ): Result<Unit>

    suspend fun signOut(): Result<Unit>

    suspend fun updateDisplayName(name: String): Result<Unit>

    suspend fun getUserById(id: String): Result<User>
}