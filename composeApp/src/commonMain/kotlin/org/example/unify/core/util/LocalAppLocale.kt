package org.example.unify.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue


expect object LocalAppLocale {
    val current: String @Composable get

    @Composable
    infix fun provides(value: String?): ProvidedValue<*>
}