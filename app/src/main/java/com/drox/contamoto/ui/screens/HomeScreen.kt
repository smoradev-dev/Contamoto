package com.drox.contamoto.ui.screens

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.drox.contamoto.ui.navigation.Configuracion
import com.drox.contamoto.ui.navigation.Perfil
import com.drox.contamoto.ui.components.AccesoRapidoGrid
import com.drox.contamoto.ui.components.AppBottomBar
import com.drox.contamoto.ui.components.ContamotoTopBar
import com.drox.contamoto.ui.components.MantenimientoItem
import com.drox.contamoto.ui.components.CalendarioMensual
import com.drox.contamoto.ui.components.MetaDiariaCard
import com.drox.contamoto.ui.theme.*


/**
 * Pantalla principal de la app.
 *
 * Muestra el saludo, un mensaje motivacional rotativo, la meta diaria con barra de progreso,
 * los 4 botones de acceso rápido (Dashboard, Ingresos, Gastos, Mantenimiento)
 * y un resumen interactivo expandible/colapsable con indicadores del día.
 *
 * @param navController Controlador de navegación para ir a otras pantallas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val maintenanceSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val metaSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        topBar = {
            ContamotoTopBar(
                title = "Hola santiago",
                mensaje = viewModel.mensajeActual,
                onProfileClick = { navController.navigate(Perfil) },
                onSettingsClick = { navController.navigate(Configuracion) }
            )
        },
        bottomBar = { AppBottomBar(navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Spacing.lg)
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(Spacing.lg)
            ) {
                MetaDiariaCard(
                    onClick = { viewModel.showMeta() }
                )

                AccesoRapidoGrid(
                    navController = navController,
                    onShowMaintenance = { viewModel.showMaintenance() }
                )
            }

            if (viewModel.showMaintenanceSheet) {
                ModalBottomSheet(
                    onDismissRequest = { viewModel.hideMaintenance() },
                    sheetState = maintenanceSheetState,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.lg)
                            .padding(bottom = Spacing.xxl)
                    ) {
                        Text(
                            "Próximos mantenimientos",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(Spacing.md))
                        MantenimientoItem("Cambio de aceite", "En 3 días", 3)
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = Spacing.sm),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        MantenimientoItem("Revisión de frenos", "En 7 días", 7)
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = Spacing.sm),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        MantenimientoItem("Llantas", "En 15 días", 15)
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = Spacing.sm),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        MantenimientoItem("Filtro de aire", "En 30 días", 30)
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = Spacing.sm),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        MantenimientoItem("Cadena de transmisión", "En 45 días", 45)
                        Spacer(Modifier.height(Spacing.xl))
                        CalendarioMensual(
                            diasConMantenimiento = mapOf(
                                3 to 3,
                                7 to 7,
                                15 to 15,
                                30 to 30,
                                45 to 45
                            ),
                            nombresMantenimiento = mapOf(
                                3 to "Cambio de aceite",
                                7 to "Revisión de frenos",
                                15 to "Llantas",
                                30 to "Filtro de aire",
                                45 to "Cadena de transmisión"
                            )
                        )
                    }
                }
            }

            if (viewModel.showMetaSheet) {
                ModalBottomSheet(
                    onDismissRequest = { viewModel.hideMeta() },
                    sheetState = metaSheetState,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.lg)
                            .padding(bottom = Spacing.xxl)
                    ) {
                        Text(
                            "Meta diaria",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(Spacing.sm))
                        Text(
                            "Sigue así, ya casi llegas a tu objetivo del día",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(Spacing.xl))

                        val extendedColors = LocalExtendedColors.current
                        val ingresos = 125_000
                        val gastos = 15_000
                        val viajes = 12
                        val ganancias = ingresos - gastos

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                        ) {
                            MiniCard(
                                label = "Ingresos",
                                value = "$${formatNumber(ingresos)}",
                                subtitle = "+12% vs ayer",
                                icon = Icons.Default.TrendingUp,
                                containerColor = extendedColors.income,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.weight(1f)
                            )
                            MiniCard(
                                label = "Gastos",
                                value = "$${formatNumber(gastos)}",
                                subtitle = "-5% vs ayer",
                                icon = Icons.Default.TrendingDown,
                                containerColor = extendedColors.expense,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(Modifier.height(Spacing.sm))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                        ) {
                            MiniCard(
                                label = "Viajes",
                                value = "$viajes",
                                subtitle = "+8% vs ayer",
                                icon = Icons.Default.DirectionsBike,
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.weight(1f)
                            )
                            MiniCard(
                                label = "Ganancia",
                                value = "$${formatNumber(ganancias)}",
                                subtitle = "+15% vs ayer",
                                icon = Icons.Default.AccountBalance,
                                containerColor = extendedColors.warning,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(Modifier.height(Spacing.lg))

                        Text(
                            "ÚLTIMOS MOVIMIENTOS",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 1.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(Spacing.md))
                        MovimientoItem(
                            titulo = "Carrera",
                            subtitulo = "Calle 10 con Cra 15",
                            monto = "$8.500",
                            tiempo = "Hace 10 min",
                            icon = Icons.Default.DirectionsBike,
                            containerColor = extendedColors.incomeContainer,
                            contentColor = extendedColors.income
                        )
                        Spacer(Modifier.height(Spacing.sm))
                        MovimientoItem(
                            titulo = "Gasolina",
                            subtitulo = "Terpel 2.5 gal",
                            monto = "$15.000",
                            tiempo = "Hace 25 min",
                            icon = Icons.Default.LocalGasStation,
                            containerColor = extendedColors.expenseContainer,
                            contentColor = extendedColors.expense
                        )
                        Spacer(Modifier.height(Spacing.sm))
                        MovimientoItem(
                            titulo = "Carrera",
                            subtitulo = "Av. Siempre Viva 742",
                            monto = "$12.000",
                            tiempo = "Hace 1 h",
                            icon = Icons.Default.DirectionsBike,
                            containerColor = extendedColors.incomeContainer,
                            contentColor = extendedColors.income
                        )
                    }
                }
            }
        }
    }
}



@Composable
private fun MiniCard(
    label: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    containerColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.aspectRatio(1f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = label,
                modifier = Modifier.size(28.dp),
                tint = contentColor
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                label.uppercase(),
                fontSize = 10.sp,
                letterSpacing = 0.5.sp,
                color = contentColor,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            Text(
                subtitle,
                fontSize = 11.sp,
                color = contentColor.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun MovimientoItem(
    titulo: String,
    subtitulo: String,
    monto: String,
    tiempo: String,
    icon: ImageVector,
    containerColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(Spacing.md),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(containerColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = titulo,
                    modifier = Modifier.size(24.dp),
                    tint = contentColor
                )
            }
            Column {
                Text(
                    titulo,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    subtitulo,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                monto,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                tiempo,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}

private fun formatNumber(n: Int): String {
    val s = n.toString()
    val sb = StringBuilder()
    var count = 0
    for (i in s.lastIndex downTo 0) {
        if (count > 0 && count % 3 == 0) sb.insert(0, ".")
        sb.insert(0, s[i])
        count++
    }
    return sb.toString()
}

