package org.example.unify.core.domain.entity

import androidx.compose.ui.unit.LayoutDirection
import org.jetbrains.compose.resources.StringResource
import unify.composeapp.generated.resources.Res
import unify.composeapp.generated.resources.arabic
import unify.composeapp.generated.resources.english

enum class Language(val code: String, val layoutDirection: LayoutDirection) {
    English("en", LayoutDirection.Ltr),
    Arabic("ar", LayoutDirection.Rtl);

    fun resource(): StringResource {
        return when (this) {
            English -> Res.string.english
            Arabic -> Res.string.arabic
        }
    }
}