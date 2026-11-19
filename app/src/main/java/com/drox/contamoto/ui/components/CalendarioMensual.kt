package com.drox.contamoto.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drox.contamoto.ui.theme.LocalExtendedColors
import com.drox.contamoto.ui.theme.Spacing
import java.time.LocalDate

val meses = arrayOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
val diasSemana = arrayOf("D", "L", "M", "M", "J", "V", "S")

@Composable
fun CalendarioMensual(
    diasConMantenimiento: Map<Int, Int> = emptyMap(),
    nombresMantenimiento: Map<Int, String> = emptyMap()
) {
    val today = LocalDate.now()
    val mes = today.monthValue - 1
    val anio = today.year
    val diasEnMes = today.lengthOfMonth()
    val primerDia = today.withDayOfMonth(1).dayOfWeek.value - 1

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "${meses[mes]} $anio",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(Spacing.sm))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            diasSemana.forEach { dia ->
                Text(
                    dia,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.width(32.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(Modifier.height(Spacing.xs))
        var day = 1
        val rows = (diasEnMes + primerDia + 6) / 7
        for (row in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (col in 0 until 7) {
                    val idx = row * 7 + col
                    if (idx < primerDia || day > diasEnMes) {
                        Box(modifier = Modifier.size(32.dp))
                    } else {
                        val diaActual = LocalDate.now().dayOfMonth
                        val hoy = diaActual == day
                        val diasRest = diasConMantenimiento[day]
                        val warning = LocalExtendedColors.current.warning
                        val expense = LocalExtendedColors.current.expense
                        val colorDia = if (diasRest != null) lerp(
                            warning, expense,
                            (1f - (diasRest.coerceIn(1, 15) - 1f) / 14f).coerceIn(0f, 1f)
                        ) else if (hoy) warning else MaterialTheme.colorScheme.onSurface
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .then(
                                    if (hoy || diasRest != null) Modifier
                                        .clip(CircleShape)
                                        .background(colorDia.copy(alpha = 0.2f))
                                    else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "$day",
                                fontSize = 12.sp,
                                fontWeight = if (hoy) FontWeight.Bold else FontWeight.Normal,
                                color = colorDia
                            )
                        }
                        day++
                    }
                }
            }
        }

        if (nombresMantenimiento.isNotEmpty()) {
            Spacer(Modifier.height(Spacing.md))
            nombresMantenimiento.forEach { (dia, nombre) ->
                val diasRest = diasConMantenimiento[dia] ?: return@forEach
                val warning = LocalExtendedColors.current.warning
                val expense = LocalExtendedColors.current.expense
                val color = lerp(
                    warning, expense,
                    (1f - (diasRest.coerceIn(1, 15) - 1f) / 14f).coerceIn(0f, 1f)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                    Text(
                        "Día $dia — $nombre",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
