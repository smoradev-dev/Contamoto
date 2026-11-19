package com.drox.contamoto.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    var showMetaSheet by mutableStateOf(false)
    var showMaintenanceSheet by mutableStateOf(false)//variable qpara mostrar o no la pantalla de mantenimiento
    var mensajeActual by mutableStateOf("") //variable que elige el mensaje actual

    private val mensajes = listOf(
        "¡Cada viaje cuenta!",
        "Ya llevas el 71% de tu meta diaria",
        "Sigue así, ¡vas por buen camino!",
        "Un viaje más y llegas a la meta",
        "Tu esfuerzo de hoy es tu recompensa de mañana",
        "¡Meta diaria a la vista!",
        "El éxito está en cada kilómetro",
        "Hoy ya ganaste \$85.500, ¡sigue así!"
    )

    init {//inicializa la clase despues de crearla y le pone un mensaje rando
        mensajeActual = mensajes.random()
    }

    fun showMeta() {
        showMetaSheet = true
    }

    fun hideMeta() {
        showMetaSheet = false
    }

    fun showMaintenance() {//funcion que muestra mantenimiento
        showMaintenanceSheet = true
    }

    fun hideMaintenance() {//funcion que esconde mantenimiento
        showMaintenanceSheet = false
    }
}
