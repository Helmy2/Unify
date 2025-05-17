package org.example.unify.core.domain.entity

import org.jetbrains.compose.resources.StringResource
import unify.composeapp.generated.resources.Res
import unify.composeapp.generated.resources.arabic
import unify.composeapp.generated.resources.english

enum class Language {
    English,
    Arabic;

    fun resource(): StringResource {
        return when (this) {
            English -> Res.string.english
            Arabic -> Res.string.arabic
        }
    }
}