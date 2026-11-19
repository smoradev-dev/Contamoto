package com.drox.contamoto.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.drox.contamoto.ui.navigation.Mantenimiento
import com.drox.contamoto.ui.theme.LocalExtendedColors
import com.drox.contamoto.ui.theme.Spacing

data class CategoriaMantenimiento(
    val nombre: String,
    val icono: ImageVector,
    val color: androidx.compose.ui.graphics.Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MantenimientoCategoriaScreen(
    navController: NavController,
    amount: Int
) {
    val extendedColors = LocalExtendedColors.current
    val categoriasMantenimiento = listOf(
        CategoriaMantenimiento("Cambio de aceite", Icons.Default.Colorize, extendedColors.warning),
        CategoriaMantenimiento("Frenos", Icons.Default.Build, extendedColors.expense),
        CategoriaMantenimiento("Llantas", Icons.Default.RadioButtonChecked, MaterialTheme.colorScheme.primary),
        CategoriaMantenimiento("Filtro de aire", Icons.Default.Air, extendedColors.income),
        CategoriaMantenimiento("Cadena", Icons.Default.Link, extendedColors.amarillo),
        CategoriaMantenimiento("Batería", Icons.Default.BatteryFull, MaterialTheme.colorScheme.secondary),
        CategoriaMantenimiento("Otro", Icons.Default.MoreHoriz, MaterialTheme.colorScheme.onSurfaceVariant)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
                title = { Text("Tipo de servicio", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = extendedColors.warning,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Spacing.xl)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = extendedColors.warningContainer)
            ) {
                Column(
                    modifier = Modifier.padding(Spacing.lg),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Costo del servicio", style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = "$${String.format("%,d", amount)}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = extendedColors.warning
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.xl))

            Text(
                text = "Selecciona el tipo de servicio",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = Spacing.lg)
            )

            categoriasMantenimiento.forEach { categoria ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Spacing.xs)
                        .clickable {
                            navController.popBackStack<Mantenimiento>(inclusive = true)
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = categoria.color.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.lg),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = categoria.icono,
                            contentDescription = categoria.nombre,
                            tint = categoria.color,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(Spacing.lg))
                        Text(
                            text = categoria.nombre,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}
