package org.example.unify.core.domain.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import unify.composeapp.generated.resources.dashboard
import unify.composeapp.generated.resources.inventory
import unify.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.StringResource
import unify.composeapp.generated.resources.Res


data class TopLevelRoute(val name: StringResource, val route: Destination, val icon: ImageVector)

object TopLevelRoutes {
    val routes = listOf(
        TopLevelRoute(Res.string.dashboard, Destination.Main.Dashboard, Icons.Default.Dashboard),
        TopLevelRoute(Res.string.inventory, Destination.Main.Inventory, Icons.Default.Inventory),
        TopLevelRoute(Res.string.settings, Destination.Main.Settings, Icons.Default.Settings),
    )
}