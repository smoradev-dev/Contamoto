package com.drox.contamoto.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    var selectedFilter by mutableIntStateOf(1)
    var isLoading by mutableStateOf(true)

    val filters = listOf("Día", "Semana", "Mes")

    val bars: List<BarData>
        get() = when (selectedFilter) {
            0 -> listOf(BarData("Hoy", 0.50f, 0.25f))
            1 -> listOf(
                BarData("Lun", 0.60f, 0.20f),
                BarData("Mar", 0.55f, 0.25f),
                BarData("Mié", 0.70f, 0.22f),
                BarData("Jue", 0.75f, 0.30f),
                BarData("Vie", 0.85f, 0.35f),
                BarData("Sáb", 1.00f, 0.40f),
                BarData("Dom", 0.45f, 0.15f),
            )
            else -> listOf(
                BarData("Sem 1", 0.70f, 0.25f),
                BarData("Sem 2", 0.80f, 0.30f),
                BarData("Sem 3", 0.65f, 0.28f),
                BarData("Sem 4", 0.90f, 0.35f),
            )
        }

    val summaryIngresos: String
        get() = when (selectedFilter) {
            0 -> "$50.000"
            1 -> "$596.500"
            else -> "$2.400.000"
        }

    val summaryGastos: String
        get() = when (selectedFilter) {
            0 -> "$12.000"
            1 -> "$160.200"
            else -> "$680.000"
        }

    val summarySaldo: String
        get() = when (selectedFilter) {
            0 -> "$38.000"
            1 -> "$436.300"
            else -> "$1.720.000"
        }

    val totalGastos: String
        get() = summaryGastos

    val periodTitle: String
        get() = when (selectedFilter) {
            0 -> "Resumen · Hoy"
            1 -> "Resumen · Semana"
            else -> "Resumen · Mes"
        }

    init {
        viewModelScope.launch {
            delay(600L)
            isLoading = false
        }
    }

    fun selectFilter(index: Int) {
        selectedFilter = index
    }
}
