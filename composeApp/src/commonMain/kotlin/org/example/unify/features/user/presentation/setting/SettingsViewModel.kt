package org.example.unify.features.user.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.unify.features.user.domain.usecase.ChangeLanguageUseCase
import org.example.unify.features.user.domain.usecase.ChangeThemeModeUseCase
import org.example.unify.features.user.domain.usecase.GetLanguageUseCase
import org.example.unify.features.user.domain.usecase.GetThemeModeUseCase
import org.example.unify.core.domain.entity.Language
import org.example.unify.core.domain.entity.ThemeMode
import org.example.unify.core.domain.navigation.Destination
import org.example.unify.core.domain.navigation.Navigator
import org.example.unify.core.domain.snackbar.SnackbarManager
import org.example.unify.features.user.domain.usecase.CurrentUserFlowUseCase
import org.example.unify.features.user.domain.usecase.LogoutUseCase
import org.example.unify.features.user.domain.usecase.UpdateNameUseCase

class SettingsViewModel(
    private val currentUserFlowUseCase: CurrentUserFlowUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val updateNameUseCase: UpdateNameUseCase,
    private val snackbarManager: SnackbarManager,
    private val getThemeModeUseCase: GetThemeModeUseCase,
    private val changeThemeModeUseCase: ChangeThemeModeUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val changeLanguageUseCase: ChangeLanguageUseCase,
    private val navigator: Navigator,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .onStart {
            initializeUserData()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SettingsState()
        )

    fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.Logout -> logout()
            is SettingsEvent.UpdateEditeNameDialog -> updateEditeNameDialog(event.show)
            is SettingsEvent.UpdateName -> updateName(event.name)
            is SettingsEvent.ConfirmUpdateName -> confirmUpdate()
            is SettingsEvent.UpdateThemeMode -> updateTheme(event.mode)
            is SettingsEvent.UpdateLanguage -> updateLanguage(event.language)
            is SettingsEvent.UpdateLanguageDialog -> updateLangeDialog(event.show)
            is SettingsEvent.UpdateThemeDialog -> updateThemeDialog(event.show)
        }
    }

    private fun updateThemeDialog(show: Boolean) {
        _state.update { it.copy(showThemeDialog = show) }
    }

    private fun updateLangeDialog(show: Boolean) {
        _state.update { it.copy(showLanguageDialog = show) }
    }

    private fun updateLanguage(language: Language) {
        viewModelScope.launch {
            changeLanguageUseCase(language)
        }
    }

    private fun updateTheme(mode: ThemeMode) {
        viewModelScope.launch {
            changeThemeModeUseCase(mode)
        }
    }


    private fun confirmUpdate() {
        viewModelScope.launch {
            updateEditeNameDialog(false)
            val result = updateNameUseCase(state.value.name)
            result.fold(
                onSuccess = {
                    snackbarManager.showSnackbar("Name updated successfully")
                    _state.update { it.copy(user = it.user?.copy(name = state.value.name)) }
                },
                onFailure = {
                    snackbarManager.showErrorSnackbar(it.message.orEmpty(), it)
                }
            )
        }
    }

    private fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    private fun updateEditeNameDialog(show: Boolean) {
        _state.update { it.copy(showEditNameDialog = show) }
    }

    private fun initializeUserData() {
        viewModelScope.launch {
            launch {
                currentUserFlowUseCase().collectLatest { result ->
                    _state.update {
                        it.copy(
                            user = result.getOrNull(),
                            name = result.getOrNull()?.name ?: ""
                        )
                    }
                }
            }
            launch {
                getThemeModeUseCase().collectLatest { result ->
                    _state.update { it.copy(themeMode = result) }
                }
            }
            launch {
                getLanguageUseCase().collectLatest { result ->
                    _state.update { it.copy(language = result) }
                }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            val result = logoutUseCase()
            result.fold(
                onSuccess = {
                    navigator.navigateAsStart(Destination.Auth)
                },
                onFailure = {
                    snackbarManager.showErrorSnackbar(it.message.orEmpty(), it)
                }
            )
        }
    }
}