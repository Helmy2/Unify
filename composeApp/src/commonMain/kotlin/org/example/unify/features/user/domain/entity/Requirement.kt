package org.example.unify.features.user.domain.entity

import org.jetbrains.compose.resources.StringResource


data class Requirement(
    val text: StringResource,
    val isMet: Boolean
)