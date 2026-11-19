package com.drox.contamoto.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.drox.contamoto.ui.screens.ConfiguracionScreen
import com.drox.contamoto.ui.screens.DashboardScreen
import com.drox.contamoto.ui.screens.GastoCategoriaScreen
import com.drox.contamoto.ui.screens.GastosScreen
import com.drox.contamoto.ui.screens.HistorialScreen
import com.drox.contamoto.ui.screens.HomeScreen
import com.drox.contamoto.ui.screens.IngresoCategoriaScreen
import com.drox.contamoto.ui.screens.IngresosScreen
import com.drox.contamoto.ui.screens.MantenimientoCategoriaScreen
import com.drox.contamoto.ui.screens.MantenimientoMontoScreen
import com.drox.contamoto.ui.screens.MantenimientoScreen
import com.drox.contamoto.ui.screens.PerfilScreen

val LocalWindowWidthClass = compositionLocalOf { WindowWidthSizeClass.Compact }

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val activity = LocalContext.current as ComponentActivity
    val windowSizeClass = calculateWindowSizeClass(activity)
    val widthSizeClass = windowSizeClass.widthSizeClass

    CompositionLocalProvider(LocalWindowWidthClass provides widthSizeClass) {
    NavHost(
        navController = navController,
        startDestination = Home,
        enterTransition = { slideInHorizontally(tween(300)) { it } + fadeIn(tween(300)) },
        exitTransition = { slideOutHorizontally(tween(300)) { -it / 3 } + fadeOut(tween(200)) },
        popEnterTransition = { slideInHorizontally(tween(300)) { -it } + fadeIn(tween(300)) },
        popExitTransition = { slideOutHorizontally(tween(300)) { it / 3 } + fadeOut(tween(200)) }
    ) {
        composable<Home> { HomeScreen(navController) }
        composable<Dashboard> { DashboardScreen(navController) }
        composable<Ingresos> { IngresosScreen(navController) }
        composable<IngresoCategoria> { backStackEntry ->
            val args: IngresoCategoria = backStackEntry.toRoute()
            IngresoCategoriaScreen(navController, args.amount)
        }
        composable<Gastos> { GastosScreen(navController) }
        composable<GastoCategoria> { backStackEntry ->
            val args: GastoCategoria = backStackEntry.toRoute()
            GastoCategoriaScreen(navController, args.amount)
        }
        composable<Mantenimiento> { MantenimientoScreen(navController) }
        composable<MantenimientoMonto> { MantenimientoMontoScreen(navController) }
        composable<MantenimientoCategoria> { backStackEntry ->
            val args: MantenimientoCategoria = backStackEntry.toRoute()
            MantenimientoCategoriaScreen(navController, args.amount)
        }
        composable<Configuracion> { ConfiguracionScreen(navController) }
        composable<Perfil> { PerfilScreen(navController) }
        composable<Historial> { HistorialScreen(navController) }
    }
    }
}
