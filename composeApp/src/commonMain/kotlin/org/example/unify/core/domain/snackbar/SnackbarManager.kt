package org.example.unify.core.domain.snackbar

import androidx.compose.material3.SnackbarHostState

interface SnackbarManager {
    val snackbarHostState: SnackbarHostState
    suspend fun showErrorSnackbar(value: String, exception: Throwable)
    suspend fun showSnackbar(value: String)
    suspend fun dismissSnackbar()
}