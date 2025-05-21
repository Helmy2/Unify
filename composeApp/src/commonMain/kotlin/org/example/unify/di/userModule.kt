package org.example.unify.di

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.minimalSettings
import io.github.jan.supabase.createSupabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.unify.BuildKonfig
import org.example.unify.features.user.data.exception.AuthExceptionMapper
import org.example.unify.features.user.data.repository.UserRepoImpl
import org.example.unify.features.user.domain.repository.UserRepo
import org.example.unify.features.user.domain.usecase.ChangeLanguageUseCase
import org.example.unify.features.user.domain.usecase.CurrentUserFlowUseCase
import org.example.unify.features.user.domain.usecase.GetDisplayNameUseCase
import org.example.unify.features.user.domain.usecase.GetLanguageUseCase
import org.example.unify.features.user.domain.usecase.IsUserLongedInUseCase
import org.example.unify.features.user.domain.usecase.LogoutUseCase
import org.example.unify.features.user.domain.usecase.RegisterUseCase
import org.example.unify.features.user.domain.usecase.UpdateNameUseCase
import org.example.unify.features.user.domain.usecase.login.IsUserLongedInFlowUseCase
import org.example.unify.features.user.domain.usecase.login.LoginUseCase
import org.example.unify.features.user.presentation.login.LoginViewModel
import org.example.unify.features.user.presentation.register.RegisterViewModel
import org.example.unify.features.user.presentation.setting.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val userModule = module {
    single<UserRepo> {
        val adminClient = createSupabaseClient(
            supabaseKey = BuildKonfig.supabaseKey, supabaseUrl = BuildKonfig.supabaseUrl
        ) {
            install(Auth) {
                minimalSettings()
            }
        }

        UserRepoImpl(
            supabaseClient = get(),
            adminClient = adminClient,
            exceptionMapper = AuthExceptionMapper(),
            dispatcher = Dispatchers.IO
        )
    }

    factory { IsUserLongedInUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { UpdateNameUseCase(get()) }
    factory { CurrentUserFlowUseCase(get()) }
    factory { ChangeLanguageUseCase(get()) }
    factory { GetLanguageUseCase(get()) }
    factory { GetDisplayNameUseCase(get()) }

    viewModel {
        LoginViewModel(
            { email, password -> LoginUseCase(get()).invoke(email, password) },
            { IsUserLongedInFlowUseCase(get()).invoke() },
            get(),
            get(),
        )
    }
    viewModel { RegisterViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }

}



