package org.example.unify.core.domain.entity

import org.jetbrains.compose.resources.StringResource
import unify.composeapp.generated.resources.Res
import unify.composeapp.generated.resources.dark_mode
import unify.composeapp.generated.resources.light_mode
import unify.composeapp.generated.resources.system_default

enum class ThemeMode {
    System,
    Light,
    Dark;

    fun resource(): StringResource {
        return when (this) {
            System -> Res.string.system_default
            Light -> Res.string.light_mode
            Dark -> Res.string.dark_mode
        }
    }
}



