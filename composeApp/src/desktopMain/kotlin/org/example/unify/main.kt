package org.example.unify

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.unify.core.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Unify",
    ) {
        App()
    }
}