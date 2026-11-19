package com.drox.contamoto.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.drox.contamoto.ui.components.ContamotoTopBarBack
import com.drox.contamoto.ui.navigation.MantenimientoCategoria
import com.drox.contamoto.ui.theme.LocalExtendedColors
import com.drox.contamoto.ui.theme.Spacing

@Composable
fun MantenimientoMontoScreen(navController: NavController) {
    var slideIn by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { slideIn = true }

    val extendedColors = LocalExtendedColors.current
    var amount by remember { mutableIntStateOf(50000) }
    var editText by remember { mutableStateOf("$50.000") }
    val quickAmounts = listOf(20000, 50000, 80000, 120000, 150000)
    fun formatAmount(n: Int) = if (n == 0) "" else "$${String.format("%,d", n).replace(",", ".")}"

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val offsetX by animateDpAsState(
            targetValue = if (slideIn) 0.dp else -maxWidth,
            animationSpec = tween(500)
        )

        Scaffold(
            modifier = Modifier.fillMaxSize().offset(x = offsetX),
            topBar = {
                ContamotoTopBarBack(
                    title = "Registrar mantenimiento",
                    subtitle = "¿Cuánto costó el servicio?",
                    containerColor = extendedColors.warning,
                    onBackClick = { navController.popBackStack() }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(extendedColors.warning.copy(alpha = 0.05f))
                    .padding(padding)
                    .padding(Spacing.xl),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Costo",
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(Spacing.sm))
                    Row(
                        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            onClick = {
                                if (amount > 0) amount -= 5000
                                editText = formatAmount(amount)
                            },
                            modifier = Modifier.weight(0.2f).fillMaxHeight(),
                            shape = RoundedCornerShape(16.dp),
                            color = extendedColors.warning.copy(alpha = 0.1f),
                            border = BorderStroke(2.dp, extendedColors.warning)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("<", color = extendedColors.warning, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        OutlinedTextField(
                            value = editText,
                            onValueChange = { newText ->
                                val raw = newText.filter { it.isDigit() }
                                if (raw.isEmpty() || raw.all { it.isDigit() }) {
                                    amount = raw.toIntOrNull() ?: 0
                                    editText = if (raw.isEmpty()) "" else formatAmount(amount)
                                }
                            },
                            textStyle = TextStyle(
                                fontSize = 40.sp, fontWeight = FontWeight.Bold, color = extendedColors.warning, textAlign = TextAlign.Center
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = extendedColors.warning,
                                unfocusedBorderColor = extendedColors.warning.copy(alpha = 0.5f),
                                cursorColor = extendedColors.warning
                            ),
                            modifier = Modifier.weight(0.6f)
                        )

                        Surface(
                            onClick = {
                                amount += 5000
                                editText = formatAmount(amount)
                            },
                            modifier = Modifier.weight(0.2f).fillMaxHeight(),
                            shape = RoundedCornerShape(16.dp),
                            color = extendedColors.warning.copy(alpha = 0.1f),
                            border = BorderStroke(2.dp, extendedColors.warning)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(">", color = extendedColors.warning, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(Modifier.height(Spacing.xs))
                    Text("pesos colombianos", style = MaterialTheme.typography.bodySmall, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(Spacing.xs))
                    Text("---paso: \$5.000---", style = MaterialTheme.typography.labelSmall, fontSize = 14.sp, color = extendedColors.warning.copy(alpha = 0.6f))
                    Spacer(Modifier.height(Spacing.xxhuge))

                    Text("Montos rápidos", style = MaterialTheme.typography.titleMedium, fontSize = 28.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(Spacing.xl))

                    Column(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm), modifier = Modifier.fillMaxWidth()) {
                            quickAmounts.take(3).forEach { q ->
                                val selected = amount == q
                                Surface(
                                    onClick = { amount = q; editText = formatAmount(q) },
                                    modifier = Modifier.weight(1f).height(40.dp),
                                    shape = RoundedCornerShape(50.dp),
                                    color = if (selected) extendedColors.warning else extendedColors.warning.copy(alpha = 0.15f),
                                    border = BorderStroke(if (selected) 2.dp else 1.dp, extendedColors.warning)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxSize().padding(horizontal = Spacing.sm)) {
                                        if (selected) { Text("✓", color = Color.White, fontSize = 12.sp); Spacer(Modifier.width(Spacing.xs)) }
                                        Text("$${q / 1000}.000", color = if (selected) Color.White else extendedColors.warning, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm), modifier = Modifier.fillMaxWidth()) {
                            quickAmounts.drop(3).forEach { q ->
                                val selected = amount == q
                                Surface(
                                    onClick = { amount = q; editText = formatAmount(q) },
                                    modifier = Modifier.weight(1f).height(40.dp),
                                    shape = RoundedCornerShape(50.dp),
                                    color = if (selected) extendedColors.warning else extendedColors.warning.copy(alpha = 0.15f),
                                    border = BorderStroke(if (selected) 2.dp else 1.dp, extendedColors.warning)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxSize().padding(horizontal = Spacing.sm)) {
                                        if (selected) { Text("✓", color = Color.White, fontSize = 12.sp); Spacer(Modifier.width(Spacing.xs)) }
                                        Text("$${q / 1000}.000", color = if (selected) Color.White else extendedColors.warning, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = { navController.navigate(MantenimientoCategoria(amount)) },
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = extendedColors.warning)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Icon(Icons.Default.Check, contentDescription = "Confirmar monto", modifier = Modifier.size(28.dp))
                        Spacer(Modifier.width(Spacing.sm))
                        Text("Aceptar", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(" - ", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.7f))
                        Text(editText.ifEmpty { "\$0" }, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
