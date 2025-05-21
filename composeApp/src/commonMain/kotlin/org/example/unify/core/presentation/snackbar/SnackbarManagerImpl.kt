package org.example.unify.core.presentation.snackbar

import androidx.compose.material3.SnackbarHostState
import org.example.unify.core.domain.snackbar.SnackbarManager


class SnackbarManagerImpl(
    val snackbarHostState: SnackbarHostState
) : SnackbarManager {

    override suspend fun showErrorSnackbar(value: String, exception: Throwable) {
        println("SnackbarManagerImpl.showErrorSnackbar: $exception")
        dismissSnackbar()
        snackbarHostState.showSnackbar(value)
    }

    override suspend fun showSnackbar(value: String) {
        dismissSnackbar()
        snackbarHostState.showSnackbar(value)
    }

    override suspend fun dismissSnackbar() {
        snackbarHostState.currentSnackbarData?.dismiss()
    }

    override fun getCurrentSnackbarHostState(): SnackbarHostState? {
        return snackbarHostState
    }
}