package org.example.unify.features.user.presentation.login

import org.jetbrains.compose.resources.StringResource

data class LoginState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
    val isPasswordVisible: Boolean = false,
)