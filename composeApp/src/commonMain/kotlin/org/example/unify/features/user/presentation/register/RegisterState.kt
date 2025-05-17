package org.example.unify.features.user.presentation.register

import org.example.unify.features.user.domain.entity.PasswordStrength
import org.example.unify.features.user.domain.entity.Requirement
import org.jetbrains.compose.resources.StringResource

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val isLoading: Boolean = false,
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
    val nameError: StringResource? = null,
    val isPasswordVisible: Boolean = false,
    val passwordStrength: PasswordStrength = PasswordStrength.WEAK,
    val passwordRequirements: List<Requirement> = emptyList()
)