package com.drox.contamoto.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.drox.contamoto.ui.components.ContamotoTopBarBack
import com.drox.contamoto.ui.components.SkeletonLoader
import com.drox.contamoto.ui.theme.*
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith

data class BarData(
    val label: String, val incomeHeight: Float, val expenseHeight: Float
)

data class GastoDistribucion(
    val nombre: String, val icono: ImageVector, val porcentaje: Int, val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = viewModel()
) {
    val selectedFilter = viewModel.selectedFilter
    val filters = viewModel.filters
    val bars = viewModel.bars
    val extendedColors = LocalExtendedColors.current
    val primary = MaterialTheme.colorScheme.primary
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    val distribucion = when (selectedFilter) {
        0 -> listOf(
            GastoDistribucion("Combustible", Icons.Default.LocalGasStation, 35, extendedColors.warning),
            GastoDistribucion("Repuestos", Icons.Default.Build, 25, primary),
            GastoDistribucion("Alimentación", Icons.Default.Restaurant, 20, extendedColors.income),
            GastoDistribucion("Papelería", Icons.Default.Description, 10, extendedColors.income),
            GastoDistribucion("Otros", Icons.Default.MoreHoriz, 10, onSurfaceVariant),
        )
        1 -> listOf(
            GastoDistribucion("Combustible", Icons.Default.LocalGasStation, 40, extendedColors.warning),
            GastoDistribucion("Repuestos", Icons.Default.Build, 20, primary),
            GastoDistribucion("Alimentación", Icons.Default.Restaurant, 15, extendedColors.income),
            GastoDistribucion("Papelería", Icons.Default.Description, 10, extendedColors.income),
            GastoDistribucion("Otros", Icons.Default.MoreHoriz, 15, onSurfaceVariant),
        )
        else -> listOf(
            GastoDistribucion("Combustible", Icons.Default.LocalGasStation, 38, extendedColors.warning),
            GastoDistribucion("Repuestos", Icons.Default.Build, 22, primary),
            GastoDistribucion("Alimentación", Icons.Default.Restaurant, 18, extendedColors.income),
            GastoDistribucion("Papelería", Icons.Default.Description, 8, extendedColors.income),
            GastoDistribucion("Otros", Icons.Default.MoreHoriz, 14, onSurfaceVariant),
        )
    }

    Scaffold(
        topBar = {
            ContamotoTopBarBack(
                title = "Estadísticas",
                onBackClick = { navController.popBackStack() },
                bottomContent = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.xxl)
                            .padding(bottom = Spacing.md)
                            .clip(RoundedCornerShape(50.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(Spacing.xs),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        filters.forEachIndexed { i, label ->
                            Surface(
                                onClick = { viewModel.selectFilter(i) },
                                shape = RoundedCornerShape(50.dp),
                                color = if (selectedFilter == i) MaterialTheme.colorScheme.surface else Color.Transparent
                            ) {
                                Text(
                                    label,
                                    fontSize = 14.sp,
                                    fontWeight = if (selectedFilter == i) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedFilter == i) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                    modifier = Modifier.padding(horizontal = Spacing.xl, vertical = Spacing.sm)
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (viewModel.isLoading) {
            SkeletonLoader(itemCount = 6)
        } else {
        AnimatedContent(
            targetState = selectedFilter,
            transitionSpec = {
                fadeIn(tween(300)) togetherWith fadeOut(tween(300))
            },
            label = "dashboardContent"
        ) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.lg),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            Spacer(Modifier.height(Spacing.sm))

            // --- Summary ---
            Card(
                shape = RoundedCornerShape(Spacing.md),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(Spacing.lg)) {
                    Text(viewModel.periodTitle, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(Spacing.lg))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                    ) {
                        SummaryTile("Ingresos", viewModel.summaryIngresos, Icons.Default.NorthEast, extendedColors.income, Modifier.weight(1f))
                        SummaryTile("Gastos", viewModel.summaryGastos, Icons.Default.SouthEast, extendedColors.expense, Modifier.weight(1f))
                        SummaryTile("Saldo", viewModel.summarySaldo, Icons.Default.ShowChart, MaterialTheme.colorScheme.primary, Modifier.weight(1f))
                    }
                }
            }

            // --- Bar Chart ---
            Card(
                shape = RoundedCornerShape(Spacing.md),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(Spacing.lg)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Ingresos vs Gastos", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
                            LegendDot(extendedColors.income, "Ingresos")
                            LegendDot(extendedColors.expense, "Gastos")
                        }
                    }
                    Spacer(Modifier.height(Spacing.lg))
                    BarChart(bars = bars, modifier = Modifier.fillMaxWidth().height(180.dp))
                }
            }

            // --- Expense Distribution ---
            Card(
                shape = RoundedCornerShape(Spacing.md),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(Spacing.lg)) {
                    Text("Distribución de gastos", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(Spacing.lg))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DonutChart(distribucion, modifier = Modifier.size(120.dp), totalAmount = viewModel.totalGastos)
                        Spacer(Modifier.width(Spacing.lg))
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                            distribucion.forEach { item ->
                                DistribucionRow(item)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(Spacing.lg))
        }
        }
        }
    }
}

@Composable
private fun SummaryTile(label: String, value: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(Spacing.md),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.xs)
        ) {
            Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(20.dp))
            Text(label.uppercase(), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color.copy(alpha = 0.7f), letterSpacing = 0.5.sp)
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = color, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun LegendDot(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(color))
        Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun BarChart(bars: List<BarData>, modifier: Modifier = Modifier) {
    val incomeColor = LocalExtendedColors.current.income
    val expenseColor = LocalExtendedColors.current.expense
    val textMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(
        fontSize = 10.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Canvas(modifier = modifier) {
        val chartHeight = size.height * 0.78f
        val barWidth = size.width / bars.size * 0.5f
        val gap = size.width / bars.size * 0.5f
        val barGap = barWidth * 0.15f
        val singleBar = (barWidth - barGap) / 2f

        val corner = CornerRadius(singleBar * 0.3f, singleBar * 0.3f)
        bars.forEachIndexed { i, bar ->
            val x = i * (barWidth + gap) + barWidth / 2f
            val incomeH = chartHeight * bar.incomeHeight
            val expenseH = chartHeight * bar.expenseHeight

            drawRoundRect(
                brush = Brush.verticalGradient(
                    colors = listOf(incomeColor, incomeColor.copy(alpha = 0.5f))
                ),
                topLeft = Offset(x - barGap - singleBar, chartHeight - incomeH),
                size = Size(singleBar, incomeH),
                cornerRadius = corner,
                style = Fill
            )
            drawRoundRect(
                brush = Brush.verticalGradient(
                    colors = listOf(expenseColor, expenseColor.copy(alpha = 0.5f))
                ),
                topLeft = Offset(x, chartHeight - expenseH),
                size = Size(singleBar, expenseH),
                cornerRadius = corner,
                style = Fill
            )

            val labelResult = textMeasurer.measure(bar.label, labelStyle)
            drawText(
                textLayoutResult = labelResult,
                topLeft = Offset(
                    x + singleBar / 2f - labelResult.size.width / 2f,
                    chartHeight + (size.height - chartHeight - labelResult.size.height) / 2f
                )
            )
        }
    }
}

@Composable
private fun DonutChart(
    items: List<GastoDistribucion>,
    modifier: Modifier = Modifier,
    totalAmount: String = "$0"
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = size.minDimension * 0.12f
            val radius = (size.minDimension - strokeWidth) / 2f
            val topLeft = Offset(
                (size.width - radius * 2 - strokeWidth) / 2f,
                (size.height - radius * 2 - strokeWidth) / 2f
            )
            var startAngle = -90f
            items.forEach { item ->
                val sweep = item.porcentaje * 3.6f
                drawArc(
                    color = item.color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = Size(radius * 2 + strokeWidth, radius * 2 + strokeWidth),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
                startAngle += sweep
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Total gastos", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(totalAmount, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun DistribucionRow(item: GastoDistribucion) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                Box(
                    modifier = Modifier.size(28.dp).clip(RoundedCornerShape(Spacing.sm)).background(item.color.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(item.icono, contentDescription = item.nombre, tint = item.color, modifier = Modifier.size(16.dp))
                }
                Text(item.nombre, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
            Text("${item.porcentaje}%", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        LinearProgressIndicator(
            progress = { item.porcentaje / 100f },
            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
            color = item.color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}
