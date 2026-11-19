package com.drox.contamoto.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavGraph
import com.drox.contamoto.ui.navigation.Dashboard
import com.drox.contamoto.ui.navigation.Historial
import com.drox.contamoto.ui.navigation.Home
import com.drox.contamoto.ui.navigation.Perfil

private data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val routeClass: Any,
)

@Composable
fun AppBottomBar(navController: NavController, modifier: Modifier = Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        BottomNavItem("Inicio", Icons.Default.Home, Home),
        BottomNavItem("Estadísticas", Icons.Default.BarChart, Dashboard),
        BottomNavItem("Historial", Icons.Default.List, Historial),
        BottomNavItem("Perfil", Icons.Default.Person, Perfil),
    )

    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, fontSize = 11.sp) },
                selected = currentDestination?.hasRoute(item.routeClass::class) == true,
                onClick = {
                    if (currentDestination?.hasRoute(item.routeClass::class) != true) {
                        navController.navigate(item.routeClass) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
