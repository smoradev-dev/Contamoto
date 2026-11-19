package com.drox.contamoto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.drox.contamoto.ui.components.SkeletonLoader
import com.drox.contamoto.ui.theme.LocalExtendedColors
import com.drox.contamoto.ui.theme.Spacing
import java.text.SimpleDateFormat
import java.util.*

data class MovimientoHistorial(
    val tipo: String,
    val descripcion: String,
    val monto: Int,
    val esIngreso: Boolean,
    val fecha: String,
    val hora: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(navController: NavController, isLoading: Boolean = false) {
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }

    val movimientos = remember {
        listOf(
            MovimientoHistorial("Viaje", "Calle 50 → Centro", 8500, true, dateFormat.format(Date()), timeFormat.format(Date())),
            MovimientoHistorial("Gasolina", "Terpel Av. Siempre Viva", 12000, false, dateFormat.format(Date()), "10:30"),
            MovimientoHistorial("Viaje", "Plaza → Terminal", 9200, true, dateFormat.format(Date()), "09:15"),
            MovimientoHistorial("Mantenimiento", "Cambio de aceite", 45000, false, "20/06/2026", "14:00"),
            MovimientoHistorial("Viaje", "Centro → Calle 80", 7500, true, "19/06/2026", "18:30"),
            MovimientoHistorial("Alimentación", "Almuerzo", 8000, false, "19/06/2026", "13:00"),
            MovimientoHistorial("Viaje", "Barrio → Centro", 8500, true, "19/06/2026", "07:20"),
            MovimientoHistorial("Repuestos", "Pastillas de freno", 25000, false, "18/06/2026", "11:00"),
            MovimientoHistorial("Viaje", "Aeropuerto → Hotel", 15000, true, "18/06/2026", "15:45"),
            MovimientoHistorial("Papelería", "SOAT", 62000, false, "17/06/2026", "09:00"),
        )
    }

    val totalIngresos = movimientos.filter { it.esIngreso }.sumOf { it.monto }
    val totalGastos = movimientos.filter { !it.esIngreso }.sumOf { it.monto }
    val extendedColors = LocalExtendedColors.current

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
                title = { Text("Historial", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(Spacing.lg)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.lg),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Ingresos", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("$${String.format("%,d", totalIngresos).replace(",", ".")}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = extendedColors.income)
                    }
                    Divider(
                        modifier = Modifier.height(40.dp).width(1.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Gastos", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("$${String.format("%,d", totalGastos).replace(",", ".")}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = extendedColors.expense)
                    }
                    Divider(
                        modifier = Modifier.height(40.dp).width(1.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Neto", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("$${String.format("%,d", totalIngresos - totalGastos).replace(",", ".")}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }

            Spacer(Modifier.height(Spacing.lg))

            if (isLoading) {
                SkeletonLoader(itemCount = 5)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    items(movimientos, key = { "${it.fecha}_${it.hora}_${it.descripcion}" }) { m ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(Spacing.md),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (m.esIngreso) extendedColors.income.copy(alpha = 0.12f) else extendedColors.expense.copy(alpha = 0.12f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        if (m.esIngreso) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                                        contentDescription = if (m.esIngreso) "Ingreso" else "Gasto",
                                        modifier = Modifier.size(20.dp),
                                        tint = if (m.esIngreso) extendedColors.income else extendedColors.expense
                                    )
                                }
                                Spacer(Modifier.width(Spacing.md))
                                Column(Modifier.weight(1f)) {
                                    Text(m.tipo, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(m.descripcion, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                                    Text("${m.fecha} · ${m.hora}", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Text(
                                    if (m.esIngreso) "+${String.format("%,d", m.monto).replace(",", ".")}" else "-${String.format("%,d", m.monto).replace(",", ".")}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (m.esIngreso) extendedColors.income else extendedColors.expense
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
