package com.drox.contamoto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.drox.contamoto.ui.components.AppBottomBar
import com.drox.contamoto.ui.navigation.Historial
import com.drox.contamoto.ui.navigation.MantenimientoMonto
import com.drox.contamoto.ui.theme.LocalExtendedColors
import com.drox.contamoto.ui.theme.Spacing

data class TareaMantenimiento(
    val titulo: String,
    val icono: ImageVector,
    val colorIcono: Color,
    val bgIcono: Color,
    val fecha: String,
    val iconoFecha: ImageVector,
    val prioridad: String,
    val colorPrioridad: Color,
    val bgPrioridad: Color,
    val colorTextPrioridad: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MantenimientoScreen(navController: NavController) {
    val extendedColors = LocalExtendedColors.current
    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary
    val tareas = remember {
        listOf(
            TareaMantenimiento(
                "Cambio de aceite", Icons.Default.OilBarrel, extendedColors.warning, extendedColors.warning.copy(alpha = 0.1f),
                "En 3 días", Icons.Default.Schedule,
                "Media", extendedColors.income, extendedColors.income.copy(alpha = 0.15f), extendedColors.income
            ),
            TareaMantenimiento(
                "Revisión de frenos", Icons.Default.PrecisionManufacturing, primary, primary.copy(alpha = 0.1f),
                "Próxima semana", Icons.Default.CalendarToday,
                "Baja", secondary, secondary.copy(alpha = 0.1f), secondary
            ),
            TareaMantenimiento(
                "Presión de neumáticos", Icons.Default.TireRepair, extendedColors.expense, extendedColors.expense.copy(alpha = 0.1f),
                "Hoy", Icons.Default.PriorityHigh,
                "Alta", extendedColors.expense, extendedColors.expense.copy(alpha = 0.15f), extendedColors.expense
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mantenimiento", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = { AppBottomBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.lg),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            // --- Summary Card ---
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(Spacing.lg),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Estado General", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(Spacing.xs))
                        Text("Excelente", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = extendedColors.income)
                        Spacer(Modifier.height(Spacing.sm))
                        Surface(
                            shape = RoundedCornerShape(50.dp),
                            color = extendedColors.income.copy(alpha = 0.1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = Spacing.sm, vertical = Spacing.xs),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = "Estado excelente", modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(Spacing.xs))
                                Text("Todo en orden", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = extendedColors.income)
                            }
                        }
                    }

                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(96.dp)) {
                        CircularProgressIndicator(
                            progress = 0.9f,
                            modifier = Modifier.size(96.dp),
                            strokeWidth = 8.dp,
                            color = extendedColors.income,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            strokeCap = StrokeCap.Round
                        )
                        Text("90%", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // --- Tasks Header ---
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = Spacing.xs),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tareas Pendientes", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Ver todas", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)
            }

            // --- Task List ---
            tareas.forEach { tarea ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(Spacing.lg),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(tarea.bgIcono),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(tarea.icono, contentDescription = tarea.titulo, tint = tarea.colorIcono, modifier = Modifier.size(32.dp))
                        }

                        Spacer(Modifier.width(Spacing.lg))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(tarea.titulo, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = tarea.bgPrioridad
                                ) {
                                    Text(
                                        tarea.prioridad.uppercase(),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = tarea.colorTextPrioridad,
                                        modifier = Modifier.padding(horizontal = Spacing.sm, vertical = Spacing.xxs)
                                    )
                                }
                            }
                            Spacer(Modifier.height(Spacing.xs))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(tarea.iconoFecha, contentDescription = "Fecha de ${tarea.titulo}", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(Spacing.xs))
                                Text(
                                    tarea.fecha,
                                    fontSize = 14.sp,
                                    color = if (tarea.prioridad == "Alta") extendedColors.expense else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = if (tarea.prioridad == "Alta") FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        }

                        Spacer(Modifier.width(Spacing.sm))
                        Icon(Icons.Default.ChevronRight, contentDescription = "Ver detalle", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            // --- Actions ---
            Button(
                onClick = { navController.navigate(MantenimientoMonto) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.AddCircle, contentDescription = "Registrar mantenimiento", modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(Spacing.sm))
                Text("Registrar nuevo mantenimiento", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            FilledTonalButton(
                onClick = { navController.navigate(Historial) },
                modifier = Modifier.fillMaxWidth().padding(vertical = Spacing.sm)
            ) {
                Icon(Icons.Default.History, contentDescription = "Historial", modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(Spacing.sm))
                Text("Historial de servicios", fontSize = 14.sp)
            }

            Spacer(Modifier.height(Spacing.lg))
        }
    }
}
