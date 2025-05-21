package org.example.unify.core.domain.navigation

import androidx.navigation.NavHostController

interface Navigator {
    fun navigateBack()
    fun navigate(route: Destination)
    fun navigateAsStart(route: Destination)
    fun getCurrentNavHost(): NavHostController?
}