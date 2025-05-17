package org.example.unify.features.user.domain.util


import unify.composeapp.generated.resources.Res
import unify.composeapp.generated.resources.password_requirement_length
import unify.composeapp.generated.resources.password_requirement_special
import unify.composeapp.generated.resources.password_requirement_uppercase
import org.example.unify.features.user.domain.entity.Requirement

fun calculatePasswordRequirements(password: String): List<Requirement> {
    return listOf(
        Requirement(Res.string.password_requirement_length, password.length >= 8),
        Requirement(
            Res.string.password_requirement_special,
            password.any { !it.isLetterOrDigit() }),
        Requirement(
            Res.string.password_requirement_uppercase,
            password.any { it.isUpperCase() })
    )
}