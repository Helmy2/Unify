package org.example.unify.core.fake

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavHostController
import org.example.unify.core.domain.navigation.Destination
import org.example.unify.core.domain.navigation.Navigator
import org.example.unify.core.domain.snackbar.SnackbarManager

class FakeSnackbarManager : SnackbarManager {
    data class SnackbarError(val message: String, val throwable: Throwable?)

    val shownErrorSnackbarList = mutableListOf<SnackbarError>()
    val shownSnackbarList = mutableListOf<String>()


    override suspend fun showErrorSnackbar(value: String, exception: Throwable) {
        shownErrorSnackbarList.add(SnackbarError(value, exception))
    }

    override suspend fun showSnackbar(value: String) {
        shownSnackbarList.add(value)
    }

    override suspend fun dismissSnackbar() {
        shownErrorSnackbarList.clear()
        shownSnackbarList.clear()
    }

    override fun getCurrentSnackbarHostState(): SnackbarHostState? {
        return null
    }

    fun clear() {
        shownErrorSnackbarList.clear()
        shownSnackbarList.clear()
    }
}

class FakeNavigator : Navigator {
    val navigatedToDestinations = mutableListOf<Destination>()

    override fun navigateBack() {
        navigatedToDestinations.removeLast()
    }

    override fun navigate(route: Destination) {
        navigatedToDestinations.add(route)
    }

    override fun navigateAsStart(route: Destination) {
        navigatedToDestinations.clear()
        navigatedToDestinations.add(route)
    }

    override fun getCurrentNavHost(): NavHostController? {
        return null
    }

    fun clear() = navigatedToDestinations.clear()

    fun lastDestination(): Destination? = navigatedToDestinations.lastOrNull()
}