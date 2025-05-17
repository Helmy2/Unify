package org.example.unify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.example.unify.core.App
import org.example.unify.core.domain.navigation.Destination
import org.example.unify.features.user.domain.usecase.IsUserLongedInUseCase
import org.jetbrains.compose.resources.stringResource
import org.koin.android.ext.android.inject
import unify.composeapp.generated.resources.Res
import unify.composeapp.generated.resources.something_went_wrong


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        val isUserLongedInUseCase: IsUserLongedInUseCase by inject()
        val startDestination: MutableStateFlow<Result<Destination>?> = MutableStateFlow(null)

        lifecycleScope.launch {
            isUserLongedInUseCase().also {
                startDestination.value =
                    it.map { flag -> if (flag) Destination.Main else Destination.Auth }
            }
        }

        splashScreen.setKeepOnScreenCondition {
            startDestination.value == null
        }

        setContent {
            val state by startDestination.collectAsStateWithLifecycle()
            state?.let {
                MaterialTheme {
                    it.onSuccess {
                        App(it)
                    }.onFailure {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(stringResource(Res.string.something_went_wrong))
                        }
                    }
                }
            }
        }
    }
}