package org.example.unify

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import org.example.unify.core.App
import org.example.unify.core.domain.navigation.Destination
import org.example.unify.core.presentation.AppTheme
import org.example.unify.di.initKoin
import org.example.unify.features.user.domain.usecase.IsUserLongedInUseCase
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import unify.composeapp.generated.resources.Res
import unify.composeapp.generated.resources.something_went_wrong

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    val isUserLongedInUseCase = koinInject<IsUserLongedInUseCase>()

    var entryDestination by remember { mutableStateOf<Result<Destination>?>(null) }

    LaunchedEffect(Unit) {
        isUserLongedInUseCase().also {
            entryDestination =
                it.map { flag -> if (flag) Destination.Main else Destination.Auth }
        }
    }
    AppTheme {
        AnimatedContent(entryDestination != null) {
            if (it) {
                entryDestination!!.onSuccess { destination ->
                    App(destination)
                }.onFailure {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(stringResource(Res.string.something_went_wrong))
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}