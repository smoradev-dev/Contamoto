package com.drox.contamoto.ui.navigation

import kotlinx.serialization.Serializable

@Serializable object Home
@Serializable object Dashboard
@Serializable object Ingresos
@Serializable data class IngresoCategoria(val amount: Int)
@Serializable object Gastos
@Serializable data class GastoCategoria(val amount: Int)
@Serializable object Mantenimiento
@Serializable object MantenimientoMonto
@Serializable data class MantenimientoCategoria(val amount: Int)
@Serializable object Configuracion
@Serializable object Perfil
@Serializable object Historial
