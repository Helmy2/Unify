package org.example.unify.core.presentation.navigation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.example.unify.core.domain.navigation.Destination
import org.example.unify.core.domain.navigation.Navigator
import org.example.unify.features.user.presentation.login.LoginRoute
import org.example.unify.features.user.presentation.register.RegisterRoute
import org.example.unify.features.user.presentation.setting.SettingsRoute
import org.koin.compose.koinInject

@Composable
fun AppNavHost(
    startDestination: Destination,
    modifier: Modifier = Modifier,
) {
    val navigator = koinInject<Navigator>()
    NavHost(
        navController = navigator.navController,
        startDestination = startDestination,
        modifier = modifier.systemBarsPadding(),
    ) {
        navigation<Destination.Main>(
            startDestination = Destination.Main.Dashboard
        ) {
            composable<Destination.Main.Dashboard> {
                Text("Dashboard")
            }
            composable<Destination.Main.Inventory> {
                Text("Inventory")
            }
            composable<Destination.Main.Settings> {
                SettingsRoute()
            }
        }

        navigation<Destination.Auth>(
            startDestination = Destination.Auth.Login
        ) {
            composable<Destination.Auth.Login> {
                LoginRoute()
            }
            composable<Destination.Auth.Register> {
                RegisterRoute()
            }
        }
    }
}

