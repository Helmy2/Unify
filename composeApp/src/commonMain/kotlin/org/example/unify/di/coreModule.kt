package org.example.unify.di

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavHostController
import org.example.unify.core.domain.navigation.Navigator
import org.example.unify.core.domain.snackbar.SnackbarManager
import org.example.unify.core.presentation.navigation.NavigatorImpl
import org.example.unify.core.presentation.snackbar.SnackbarManagerImpl
import org.example.unify.features.user.data.repository.SettingsManagerImpl
import org.example.unify.features.user.domain.repository.SettingsManager
import org.example.unify.features.user.domain.usecase.ChangeThemeModeUseCase
import org.example.unify.features.user.domain.usecase.GetThemeModeUseCase
import org.koin.dsl.module

val coreModule = module {
    single<Navigator> { (navController: NavHostController) ->
        NavigatorImpl(navController)
    }
    single<SnackbarManager> { (snackbarHostState: SnackbarHostState) ->
        SnackbarManagerImpl(snackbarHostState)
    }
    single<SettingsManager> {
        SettingsManagerImpl(get())
    }
    factory { GetThemeModeUseCase(get()) }
    single { ChangeThemeModeUseCase(get()) }
}