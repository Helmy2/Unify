package org.example.unify.features.user.presentation.register

sealed class RegisterEvent {
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    data class NameChanged(val name: String) : RegisterEvent()
    data object TogglePasswordVisibility : RegisterEvent()
    data object Register : RegisterEvent()
    data object NavigateToLogin : RegisterEvent()
}