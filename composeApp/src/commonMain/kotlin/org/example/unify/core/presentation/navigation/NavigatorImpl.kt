package org.example.unify.core.presentation.navigation

import androidx.navigation.NavHostController
import org.example.unify.core.domain.navigation.Destination
import org.example.unify.core.domain.navigation.Navigator


class NavigatorImpl(
    override val navController: NavHostController
) : Navigator {
    override fun navigate(route: Destination) {
        navController.navigate(route)
    }

    override fun navigateBack() {
        navController.navigateUp()
    }

    override fun navigateAsStart(route: Destination) {
        navController.navigate(route) {
            popUpTo(0)
        }
    }
}