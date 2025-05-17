package org.example.unify.features.user.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.put
import org.example.unify.BuildKonfig
import org.example.unify.core.domain.exceptions.ExceptionMapper
import org.example.unify.core.util.SupabaseConfig
import org.example.unify.features.user.data.exception.AuthException
import org.example.unify.features.user.domain.entity.User
import org.example.unify.features.user.domain.entity.toDomainUser
import org.example.unify.features.user.domain.repository.UserRepo

class UserRepoImpl(
    private val supabaseClient: SupabaseClient,
    private val adminClient: SupabaseClient,
    private val exceptionMapper: ExceptionMapper,
    private val dispatcher: CoroutineDispatcher,
) : UserRepo {

    init {
        CoroutineScope(dispatcher).launch {
            adminClient.auth.importAuthToken(BuildKonfig.supabaseSecret)
        }
    }

    override val currentUser: Flow<Result<User?>> = supabaseClient.auth.sessionStatus.map {
        try {
            Result.success(supabaseClient.auth.currentUserOrNull()?.toDomainUser())
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }.catch { e ->
        emit(Result.failure(exceptionMapper.map(e)))
    }

    override suspend fun getUserById(id: String): Result<User> = runCatching {
        adminClient.auth.admin.retrieveUserById(id).toDomainUser()
    }


    override suspend fun signOut(): Result<Unit> = withContext(dispatcher) {
        try {
            supabaseClient.auth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }

    override suspend fun updateDisplayName(name: String): Result<Unit> = withContext(dispatcher) {
        try {
            supabaseClient.auth.updateUser {
                data {
                    put(SupabaseConfig.DISPLAY_NAME_KEY, name)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }

    override fun isUserLongedIn(): Flow<Result<Boolean>> {
        return channelFlow {
            supabaseClient.auth.sessionStatus
                .collectLatest {
                when (it) {
                    is SessionStatus.Authenticated -> trySend(Result.success(true))
                    is SessionStatus.NotAuthenticated -> trySend(Result.success(false))
                    is SessionStatus.RefreshFailure -> Result.failure<Boolean>(
                        AuthException.AuthenticationException("Refresh Failure")
                    )
                    else -> {}
                }
            }
        }.catch {
            emit(Result.failure(exceptionMapper.map(it)))
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String, password: String
    ): Result<Unit> = withContext(dispatcher) {
        try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }

    override suspend fun createUserWithEmailAndPassword(
        name: String, email: String, password: String
    ): Result<Unit> = withContext(dispatcher) {
        try {
            supabaseClient.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            updateDisplayName(name)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }
}
