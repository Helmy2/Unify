package org.example.unify.core.domain.snackbar

import androidx.compose.material3.SnackbarHostState

interface SnackbarManager {
    suspend fun showErrorSnackbar(value: String, exception: Throwable)
    suspend fun showSnackbar(value: String)
    suspend fun dismissSnackbar()
    fun getCurrentSnackbarHostState(): SnackbarHostState?
}